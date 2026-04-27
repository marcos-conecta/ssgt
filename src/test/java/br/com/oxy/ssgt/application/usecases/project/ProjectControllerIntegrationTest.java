package br.com.oxy.ssgt.application.usecases.project;

import br.com.oxy.ssgt.infra.controller.auth.LoginRequestDTO;
import br.com.oxy.ssgt.infra.controller.project.CreateProjectDTO;
import br.com.oxy.ssgt.infra.controller.user.CreateUserDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProjectControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    private Long createUser(String name, String email) throws Exception{
        CreateUserDTO request = new CreateUserDTO(
                name,
                email,
                "123456"
        );

        String response = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode json = objectMapper.readTree(response);

        return json.get("id").asLong();
    }

    private String login(String email, String password) throws Exception {
        LoginRequestDTO request = new LoginRequestDTO(email, password);

        String response = mockMvc.perform(post("/auth/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode json = objectMapper.readTree(response);

        return json.get("token").asText();
    }

    @Test
    void shouldCreateProject() throws Exception {
        Long ownerId = createUser("Admin", "admin@test.com");
        Long memberOneId = createUser("Member one", "member1@test.com");
        Long memberTwoId = createUser("Member two", "member2@test.com");
        Long memberThreeId = createUser("Member three", "member3@test.com");

        String token = login("admin@test.com", "123456");

        CreateProjectDTO request = new CreateProjectDTO(
                "Projeto teste",
                "Projeto criado via teste de integração",
                ownerId,
                List.of(ownerId, memberOneId, memberTwoId, memberThreeId)
        );

        String response = mockMvc.perform(post("/projects")
                    .header("Authorization", "Bearer " + token)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode json = objectMapper.readTree(response);

        assertThat(json.get("id").asLong()).isPositive();
        assertThat(json.get("name").asText()).isEqualTo("Projeto teste");
        assertThat(json.get("description").asText()).isEqualTo("Projeto criado via teste de integração");
        assertThat(json.get("ownerId").asLong()).isEqualTo(ownerId);
        assertThat(json.get("members")).hasSize(4);
        assertThat(json.get("members").findValuesAsText("")).isNotNull();

    }
}
