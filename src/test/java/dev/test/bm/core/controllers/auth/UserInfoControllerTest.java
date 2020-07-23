package dev.test.bm.core.controllers.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("GIVEN no user is logged in, " +
            "WHEN the GET /me endpoint is called, " +
            "THEN a 403 response is returned")
    void testNoLogin() throws Exception {
        mockMvc.perform(get("/user/v1/me")).andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username="user")
    @DisplayName("GIVEN a user login with a single role USER, " +
            "WHEN the GET /me endpoint is called, " +
            "THEN the username and role USER is returned")
    void testUserData() throws Exception {
        mockMvc.perform(get("/user/v1/me")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("user")))
                .andExpect(jsonPath("$.roles", is(Collections.singletonList("ROLE_USER"))));
    }

    @Test
    @WithMockUser(username="admin", roles={"USER", "ADMIN"})
    @DisplayName("GIVEN a admin login with an additional ADMIN role, " +
            "WHEN the GET /me endpoint is called, " +
            "THEN the username and roles USER and ADMIN are returned")
    void testAdminData() throws Exception {
        mockMvc.perform(get("/user/v1/me")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("admin")))
                .andExpect(jsonPath("$.roles", is(Arrays.asList("ROLE_ADMIN", "ROLE_USER"))));
    }

}
