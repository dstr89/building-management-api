package dev.test.bm.core.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.test.bm.core.entities.BuildingEntity;
import dev.test.bm.core.exceptions.BuildingNotFoundException;
import dev.test.bm.core.repository.BuildingRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BuildingControllerTest {

    private static final BuildingEntity sampleBuildingOne = BuildingEntity.builder()
            .name("Test building 1")
            .city("Varazdin")
            .build();
    private static final BuildingEntity sampleBuildingTwo = BuildingEntity.builder()
            .name("Test building 2")
            .city("Zagreb")
            .build();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private BuildingRepository repository;

    @Test
    @WithMockUser(value = "user")
    @DisplayName("GIVEN two building records exist in the database, " +
            "WHEN the GET /buildings endpoint is called, " +
            "THEN the two building records are returned in JSON format")
    void testGetAllBuildingRecords() throws Exception {
        when(repository.findAll()).thenReturn(Arrays.asList(sampleBuildingOne, sampleBuildingTwo));

        mockMvc.perform(get("/api/v1/buildings")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(repository, times(1)).findAll();
    }

    @Test
    @WithMockUser(value = "user")
    @DisplayName("GIVEN a building with id=1 exists in the database, " +
            "WHEN the GET /buildings/1 endpoint is called, " +
            "THEN the record is returned in JSON format")
    void testGetOneBuildingRecord() throws Exception {
        when(repository.findById(1L)).thenReturn(Optional.of(sampleBuildingOne));

        mockMvc.perform(get("/api/v1/buildings/1")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(sampleBuildingOne.getName())))
                .andExpect(jsonPath("$.city", is(sampleBuildingOne.getCity())));

        verify(repository, times(1)).findById(1L);
    }

    @Test
    @WithMockUser(value = "user")
    @DisplayName("GIVEN an building with id=1 does not exists in the database, " +
            "WHEN the GET /buildings/1 endpoint is called, " +
            "THEN the 'Could not find employee' error is returned")
    void testGetNotExistingBuildingRecord() throws Exception {
        mockMvc.perform(get("/api/v1/buildings/1")).andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString(BuildingNotFoundException.ERROR_MSG)));
    }

    @Test
    @WithMockUser(username="admin", roles={"USER", "ADMIN"})
    @DisplayName("GIVEN an admin login and a valid json-formatted building in the request body, " +
            "WHEN the POST /buildings endpoint is called, " +
            "THEN the record is saved and returned in JSON format")
    void testSaveNewBuildingRecord() throws Exception {
        when(repository.save(sampleBuildingOne)).thenReturn(sampleBuildingOne);

        mockMvc.perform(post("/api/v1/buildings")
                .content(objectMapper.writeValueAsString(sampleBuildingOne))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(sampleBuildingOne.getName())));

        verify(repository, times(1)).save(sampleBuildingOne);
    }

    @Test
    @WithMockUser(username="user")
    @DisplayName("GIVEN a user without an ADMIN role, " +
            "WHEN the POST /buildings endpoint is called, " +
            "THEN the 403 response is returned")
    void testOnlyAdminCanCreateBuildings() throws Exception {
        mockMvc.perform(post("/api/v1/buildings")
                .content(objectMapper.writeValueAsString(sampleBuildingOne))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username="admin", roles={"USER", "ADMIN"})
    @DisplayName("GIVEN an admin login and a valid and existing  json-formatted building in the request body, " +
            "WHEN the PUT /buildings endpoint is called, " +
            "THEN the record is updated and returned in JSON format")
    void testUpdateExistingBuildingRecord() throws Exception {
        when(repository.findById(1L)).thenReturn(Optional.of(sampleBuildingTwo));
        sampleBuildingTwo.setName("New Name");
        when(repository.save(sampleBuildingTwo)).thenReturn(sampleBuildingTwo);

        mockMvc.perform(put("/api/v1/buildings/1")
                .content(objectMapper.writeValueAsString(sampleBuildingTwo))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(sampleBuildingTwo.getName())));

        verify(repository, times(1)).save(sampleBuildingTwo);
    }

    @Test
    @WithMockUser(username="admin", roles={"USER", "ADMIN"})
    @DisplayName("GIVEN an admin login and a valid, but non-existing json-formatted building in the request body, " +
            "WHEN the PUT /buildings endpoint is called, " +
            "THEN the record is updated and returned in JSON format")
    void testUpdateNonExistingBuildingRecord() throws Exception {
        mockMvc.perform(put("/api/v1/buildings/1")
                .content(objectMapper.writeValueAsString(sampleBuildingTwo))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString(BuildingNotFoundException.ERROR_MSG)));
    }

    @Test
    @WithMockUser(username="user")
    @DisplayName("GIVEN a user without an ADMIN role, " +
            "WHEN the PUT /buildings endpoint is called, " +
            "THEN the 403 response is returned")
    void testOnlyAdminCanUpdateBuildings() throws Exception {
        mockMvc.perform(put("/api/v1/buildings/1")
                .content(objectMapper.writeValueAsString(sampleBuildingTwo))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username="admin", roles={"USER", "ADMIN"})
    @DisplayName("GIVEN an admin login and a building with id=1 exists in the database, " +
            "WHEN the DELETE /buildings/1 endpoint is called, " +
            "THEN the record is deleted and an empty ok response is returned")
    void testDeleteBuilding() throws Exception {
        mockMvc.perform(delete("/api/v1/buildings/1"))
                .andExpect(status().isOk());

        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    @WithMockUser(username="user")
    @DisplayName("GIVEN a user without an ADMIN role, " +
            "WHEN the DELETE /buildings/1 endpoint is called, " +
            "THEN the 403 response is returned")
    void testOnlyAdminCanDeleteBuildings() throws Exception {
        mockMvc.perform(delete("/api/v1/buildings/1"))
                .andExpect(status().isForbidden());
    }

}
