package dev.test.bm.core.controllers.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.test.bm.core.data.AuthenticationRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

    private static final AuthenticationRequest sampleExistingUserRequest = AuthenticationRequest.builder()
            .username("user")
            .password("Ry6zNwqNW+59+tpC")
            .build();

    private static final AuthenticationRequest sampleNonExistingUserRequest = AuthenticationRequest.builder()
            .username("invalid")
            .password("password")
            .build();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("GIVEN valid and existing user credentials are provided in the request, " +
            "WHEN the POST /login endpoint is called, " +
            "THEN a valid JWT token is returned in JSON format")
    void testLoginUserOk() throws Exception {
        mockMvc.perform(post("/auth/v1/login")
                .content(objectMapper.writeValueAsString(sampleExistingUserRequest))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", not(emptyOrNullString())))
                .andReturn();
    }

    @Test
    @DisplayName("GIVEN valid, but non existing user credentials are provided in the request, " +
            "WHEN the POST /login endpoint is called, " +
            "THEN a 403 response is returned")
    void testLoginUserFailed() throws Exception {
        mockMvc.perform(post("/auth/v1/login")
                .content(objectMapper.writeValueAsString(sampleNonExistingUserRequest))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

}
