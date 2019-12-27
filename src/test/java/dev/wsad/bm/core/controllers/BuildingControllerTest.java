package dev.wsad.bm.core.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.wsad.bm.core.controllers.entities.BuildingEntity;
import dev.wsad.bm.core.controllers.exceptions.BuildingNotFoundException;
import dev.wsad.bm.core.controllers.repository.BuildingRepository;
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
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BuildingControllerTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final BuildingEntity sampleBuildingOne = new BuildingEntity("Building 1", "Varazdin");
    private static final BuildingEntity sampleBuildingTwo = new BuildingEntity("Building 2", "Zagreb");
    private static final BuildingEntity sampleBuildingThree = new BuildingEntity("Building 3", "Osijek");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BuildingRepository repository;

    @Test
    @WithMockUser(value = "spring")
    @DisplayName("GIVEN two building records exist in the database, " +
            "WHEN the GET /buildings endpoint is called, " +
            "THEN the two building records are returned in JSON format")
    void testGetAllBuildingRecords() throws Exception {
        when(repository.findAll()).thenReturn(Arrays.asList(sampleBuildingOne, sampleBuildingTwo));

        mockMvc.perform(get("/buildings")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        verify(repository, times(1)).findAll();
    }

    @Test
    @WithMockUser(value = "spring")
    @DisplayName("GIVEN a building with id=1 exists in the database, " +
            "WHEN the GET /buildings/1 endpoint is called, " +
            "THEN the record is returned in JSON format")
    void testGetOneBuildingRecord() throws Exception {
        when(repository.findById(1L)).thenReturn(Optional.of(sampleBuildingOne));

        mockMvc.perform(get("/buildings/1")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(sampleBuildingOne.getName())))
                .andExpect(jsonPath("$.city", is(sampleBuildingOne.getCity())));

        verify(repository, times(1)).findById(1L);
    }

    @Test
    @WithMockUser(value = "spring")
    @DisplayName("GIVEN an building with id=1 does not exists in the database, " +
            "WHEN the GET /building/1 endpoint is called, " +
            "THEN the 'Could not find employee' error is returned")
    void getNonExistingEmployeeShouldReturnError() throws Exception {
        mockMvc.perform(get("/building/1")).andDo(print()).andExpect(status().isNotFound())
                .andExpect(content().string(containsString(BuildingNotFoundException.ERROR_MSG)));
    }

    @Test
    @WithMockUser(value = "spring")
    @DisplayName("GIVEN an valid json-formatted building in the request body, " +
            "WHEN the POST /buildings endpoint is called, " +
            "THEN the record is saved and returned in JSON format")
    void postEmployeesShouldReturnTheInsertedRecord() throws Exception {
        when(repository.save(sampleBuildingThree)).thenReturn(sampleBuildingThree);

        mockMvc.perform(post("/buildings")
                .content(objectMapper.writeValueAsString(sampleBuildingThree))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(sampleBuildingThree.getName())));

        verify(repository, times(1)).save(sampleBuildingThree);
    }

    @Test
    @WithMockUser(value = "spring")
    @DisplayName("GIVEN an valid json-formatted building in the request body, " +
            "WHEN the PUT /building endpoint is called, " +
            "THEN the record is updated and returned in JSON format")
    void putEmployeesShouldReturnTheUpdatedRecord() throws Exception {
        when(repository.findById(1L)).thenReturn(Optional.of(sampleBuildingTwo));
        sampleBuildingTwo.setName("New Name");
        when(repository.save(sampleBuildingTwo)).thenReturn(sampleBuildingTwo);

        mockMvc.perform(put("/building/1")
                .content(objectMapper.writeValueAsString(sampleBuildingTwo))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(sampleBuildingTwo.getName())));

        verify(repository, times(1)).save(sampleBuildingTwo);
    }

    @Test
    @WithMockUser(value = "spring")
    @DisplayName("GIVEN a building with id=1 exists in the database, " +
            "WHEN the DELETE /building/1 endpoint is called, " +
            "THEN the record is deleted and an empty ok response is returned")
    void deleteEmployeesShouldReturnStatusOk() throws Exception {
        mockMvc.perform(delete("/building/1"))
                .andExpect(status().isOk());

        verify(repository, times(1)).deleteById(1L);
    }

}
