/* (C)2025 */
package com.epam.dalvaradoc.mod2_spring_core_task.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainee;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.AuthenticationDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TraineeDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.UpdateTraineeDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.repositories.TraineeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import java.sql.Date;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
class TraineesControllerTests {

    @Autowired private MockMvc mockMvc;

    @Autowired private ObjectMapper objectMapper;

    @Autowired private TraineeRepository traineeRepository;

    @Test
    void contextLoads() {
        assertNotNull(mockMvc);
        assertNotNull(objectMapper);
        assertNotNull(traineeRepository);
    }

    @Test
    @Transactional
    void createTraineeTest() throws Exception {
        TraineeDTO traineeDTO =
                TraineeDTO.builder()
                        .firstName("John")
                        .lastName("Smith")
                        .address("123 Main St")
                        .birthdate(Date.valueOf("2000-01-01"))
                        .build();

        MvcResult result =
                mockMvc.perform(
                                post("/trainees")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(traineeDTO)))
                        .andExpect(status().isOk())
                        .andReturn();

        AuthenticationDTO auth =
                objectMapper.readValue(
                        result.getResponse().getContentAsString(), AuthenticationDTO.class);

        assertNotNull(auth);
        assertEquals("John.Smith", auth.getUsername());

        Trainee trainee = traineeRepository.findByUsername(auth.getUsername());
        assertNotNull(trainee);
        assertEquals("John", trainee.getFirstName());
        assertEquals("Smith", trainee.getLastName());
        assertEquals("123 Main St", trainee.getAddress());

        traineeRepository.deleteByUsername(auth.getUsername());
    }

    @Test
    void createBadTraineeTest() throws Exception {
        TraineeDTO badTraineeDTO1 =
                TraineeDTO.builder()
                        .firstName("John123")
                        .lastName("Smith")
                        .address("123 Main St")
                        .birthdate(Date.valueOf("2000-01-01"))
                        .build();

        mockMvc.perform(
                        post("/trainees")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(badTraineeDTO1)))
                .andExpect(status().isBadRequest());

        TraineeDTO badTraineeDTO2 =
                TraineeDTO.builder()
                        .firstName("John")
                        .lastName("Smith123")
                        .address("123 Main St")
                        .birthdate(Date.valueOf("2000-01-01"))
                        .build();

        mockMvc.perform(
                        post("/trainees")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(badTraineeDTO2)))
                .andExpect(status().isBadRequest());

        TraineeDTO badTraineeDTO3 =
                TraineeDTO.builder()
                        .firstName("John")
                        .lastName("Smith")
                        .address("123 Main St")
                        .birthdate(Date.valueOf("2027-01-01"))
                        .build();

        mockMvc.perform(
                        post("/trainees")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(badTraineeDTO3)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void updateTraineeTest() throws Exception {
        // Create a test trainee first
        TraineeDTO traineeDTO =
                TraineeDTO.builder()
                        .firstName("Alice")
                        .lastName("Brown")
                        .address("789 Pine St")
                        .birthdate(Date.valueOf("1990-03-10"))
                        .build();

        MvcResult createResult =
                mockMvc.perform(
                                post("/trainees")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(traineeDTO)))
                        .andExpect(status().isOk())
                        .andReturn();

        AuthenticationDTO auth =
                objectMapper.readValue(
                        createResult.getResponse().getContentAsString(), AuthenticationDTO.class);

        UpdateTraineeDTO updateDTO =
                UpdateTraineeDTO.builder()
                        .firstName("AliceUpdated")
                        .lastName("BrownUpdated")
                        .address("New Address")
                        .isActive(true)
                        .auth(auth)
                        .build();

        MvcResult updateResult =
                mockMvc.perform(
                                put("/trainees")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(updateDTO)))
                        .andExpect(status().isOk())
                        .andReturn();

        TraineeDTO updated =
                objectMapper.readValue(
                        updateResult.getResponse().getContentAsString(), TraineeDTO.class);

        assertNotNull(updated);
        assertEquals("AliceUpdated", updated.getFirstName());
        assertEquals("BrownUpdated", updated.getLastName());
        assertEquals("New Address", updated.getAddress());

        traineeRepository.deleteByUsername(auth.getUsername());
    }

    @Test
    @Transactional
    void deleteTraineeTest() throws Exception {
        TraineeDTO traineeDTO =
                TraineeDTO.builder()
                        .firstName("Eric")
                        .lastName("Johnson")
                        .address("741 Maple St")
                        .birthdate(Date.valueOf("1995-03-15"))
                        .build();

        MvcResult createResult =
                mockMvc.perform(
                                post("/trainees")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(traineeDTO)))
                        .andExpect(status().isOk())
                        .andReturn();

        AuthenticationDTO auth =
                objectMapper.readValue(
                        createResult.getResponse().getContentAsString(), AuthenticationDTO.class);

        assertNotNull(traineeRepository.findByUsername(auth.getUsername()));

        mockMvc.perform(
                        delete("/trainees")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(auth)))
                .andExpect(status().isNoContent());

        assertNull(traineeRepository.findByUsername(auth.getUsername()));
    }

    @Test
    @Transactional
    void changeActiveStateTest() throws Exception {
        TraineeDTO traineeDTO =
                TraineeDTO.builder()
                        .firstName("David")
                        .lastName("Miller")
                        .address("987 Pine St")
                        .birthdate(Date.valueOf("1987-09-25"))
                        .build();

        MvcResult createResult =
                mockMvc.perform(
                                post("/trainees")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(traineeDTO)))
                        .andExpect(status().isOk())
                        .andReturn();

        AuthenticationDTO auth =
                objectMapper.readValue(
                        createResult.getResponse().getContentAsString(), AuthenticationDTO.class);

        Trainee traineeBefore = traineeRepository.findByUsername(auth.getUsername());
        assertTrue(traineeBefore.isActive());

        mockMvc.perform(
                        patch("/trainees/{username}/set-active-state", auth.getUsername())
                                .param("active", "false")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(auth)))
                .andExpect(status().isOk());

        Trainee trainee = traineeRepository.findByUsername(auth.getUsername());
        assertFalse(trainee.isActive());

        traineeRepository.deleteByUsername(auth.getUsername());
    }
}
