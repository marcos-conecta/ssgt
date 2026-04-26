package br.com.oxy.ssgt.infra.controller.auth;

import br.com.oxy.ssgt.application.usecases.FindUserByEmail;
import br.com.oxy.ssgt.domain.entities.user.User;
import br.com.oxy.ssgt.infra.execption.NotFoundException;
import br.com.oxy.ssgt.infra.execption.UnauthorizedException;
import br.com.oxy.ssgt.infra.security.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final FindUserByEmail findUserByEmail;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthController(FindUserByEmail findUserByEmail, PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.findUserByEmail = findUserByEmail;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @Operation(description = "Authenticate a user and return a JWT token")
    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO dto) {
        User user = findUserByEmail.execute(dto.email());

        if(!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new UnauthorizedException("Invalid credentials");
        }
        String token = tokenService.generateToken(user);
        return new LoginResponseDTO(token);
    }
}
