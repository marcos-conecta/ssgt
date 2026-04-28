package br.com.oxy.ssgt.infra.gateways;


import br.com.oxy.ssgt.application.gateways.UserRepositoryApplication;
import br.com.oxy.ssgt.domain.entities.user.User;
import br.com.oxy.ssgt.infra.execption.NotFoundException;
import br.com.oxy.ssgt.infra.persistence.user.UserEntity;
import br.com.oxy.ssgt.infra.persistence.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class UserRepositoryJPA implements UserRepositoryApplication {

    private final UserRepository repository;
    private final UserEntityMapper mapper;

    public UserRepositoryJPA(UserRepository repository, UserEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public User registerUser(User user) {
        UserEntity entity = mapper.toEntity(user);
        repository.save(entity);
        return mapper.toDomain(entity);
    }

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toDomain);
    }

    @Override
    public User findById(Long userId) {
        return repository.findById(userId)
                .map(mapper::toDomain)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));
    }

    @Override
    public void deleteUser(Long userId) {
        repository.deleteById(userId);

    }

    @Override
    public User updateUser(User user) {
        UserEntity entity = mapper.toEntity(user);
        repository.save(entity);
        return mapper.toDomain(entity);
    }

    @Override
    public User findByEmail(String email) {
        UserEntity entity = repository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found with email: "+ email));
        return mapper.toDomain(entity);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
}
