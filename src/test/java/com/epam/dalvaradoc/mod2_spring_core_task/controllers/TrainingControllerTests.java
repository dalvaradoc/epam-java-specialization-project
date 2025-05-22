package com.epam.dalvaradoc.mod2_spring_core_task.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.sql.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainee;
import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainer;
import com.epam.dalvaradoc.mod2_spring_core_task.dao.Training;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.AuthenticationDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TraineeDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainerDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainingDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainingTypeDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.repositories.TraineeRepository;
import com.epam.dalvaradoc.mod2_spring_core_task.repositories.TrainerRepository;
import com.epam.dalvaradoc.mod2_spring_core_task.repositories.TrainingRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
class TrainingControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TrainingRepository trainingRepository;

    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private TraineeRepository traineeRepository;

    @Test
    void contextLoads() {
        assertNotNull(mockMvc);
        assertNotNull(objectMapper);
        assertNotNull(trainingRepository);
    }

    @Test
    void isInitializing() {
        assertEquals(50, trainingRepository.findAll().size());
        assertTrue(trainingRepository.findByName("at turpis a").isPresent());
    }

    @Test
    @Transactional
    void getAllTrainingTypesTest() throws Exception {
        Trainer trainer = trainerRepository.findById("26").orElse(null);
        assertNotNull(trainer);

        AuthenticationDTO auth = new AuthenticationDTO(trainer.getUsername(), trainer.getPassword());

        MvcResult result = mockMvc.perform(get("/trainings/types")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(auth)))
                .andExpect(status().isOk())
                .andReturn();

        List<TrainingTypeDTO> types = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<List<TrainingTypeDTO>>() {
                });

        assertNotNull(types);
        assertFalse(types.isEmpty());
    }

    @Test
    @Transactional
    void createTrainingTest() throws Exception {
        Training existingTraining = trainingRepository.findAll().get(0);
        Trainee trainee = existingTraining.getTrainee();
        Trainer trainer = existingTraining.getTrainer();

        TrainingDTO trainingDTO = TrainingDTO.builder()
                .name("Test Training")
                .type(TrainingTypeDTO.builder().name("CARDIO").build())
                .date(Date.valueOf("2026-04-21"))
                .duration(10)
                .trainee(TraineeDTO.builder()
                        .firstName(trainee.getFirstName())
                        .lastName(trainee.getLastName())
                        .auth(new AuthenticationDTO(trainee.getUsername(), trainee.getPassword()))
                        .build())
                .trainer(TrainerDTO.builder()
                        .firstName(trainer.getFirstName())
                        .lastName(trainer.getLastName())
                        .auth(new AuthenticationDTO(trainer.getUsername(), trainer.getPassword()))
                        .build())
                .auth(new AuthenticationDTO(trainee.getUsername(), trainee.getPassword()))
                .build();

        MvcResult result = mockMvc.perform(post("/trainings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(trainingDTO)))
                .andExpect(status().isOk())
                .andReturn();

        TrainingDTO createdTraining = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                TrainingDTO.class);

        assertNotNull(createdTraining);
        assertEquals("Test Training", createdTraining.getName());
        assertEquals("CARDIO", createdTraining.getType().getName());
        assertEquals(10, createdTraining.getDuration());

        // Cleanup
        trainingRepository.deleteByName(createdTraining.getName());
    }

    @Test
    void createBadTrainingTest() throws Exception {
        Training existingTraining = trainingRepository.findAll().get(0);
        Trainee trainee = existingTraining.getTrainee();
        Trainer trainer = existingTraining.getTrainer();

        // Test missing name
        TrainingDTO badTrainingDTO = TrainingDTO.builder()
                .type(TrainingTypeDTO.builder().name("CARDIO").build())
                .date(Date.valueOf("2025-04-21"))
                .duration(10)
                .trainee(TraineeDTO.builder()
                        .firstName(trainee.getFirstName())
                        .lastName(trainee.getLastName())
                        .auth(new AuthenticationDTO(trainee.getUsername(), trainee.getPassword()))
                        .build())
                .trainer(TrainerDTO.builder()
                        .firstName(trainer.getFirstName())
                        .lastName(trainer.getLastName())
                        .auth(new AuthenticationDTO(trainer.getUsername(), trainer.getPassword()))
                        .build())
                .auth(new AuthenticationDTO(trainee.getUsername(), trainee.getPassword()))
                .build();

        mockMvc.perform(post("/trainings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(badTrainingDTO)))
                .andExpect(status().isBadRequest());

        // Test negative duration
        badTrainingDTO = badTrainingDTO.toBuilder()
                .name("Test Training")
                .duration(-1)
                .build();

        mockMvc.perform(post("/trainings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(badTrainingDTO)))
                .andExpect(status().isBadRequest());

        // Test missing trainee
        badTrainingDTO = badTrainingDTO.toBuilder()
                .duration(10)
                .trainee(null)
                .build();

        mockMvc.perform(post("/trainings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(badTrainingDTO)))
                .andExpect(status().isBadRequest());

        // Test missing trainer
        badTrainingDTO = badTrainingDTO.toBuilder()
                .trainee(TraineeDTO.builder()
                        .firstName(trainee.getFirstName())
                        .lastName(trainee.getLastName())
                        .auth(new AuthenticationDTO(trainee.getUsername(), trainee.getPassword()))
                        .build())
                .trainer(null)
                .build();

        mockMvc.perform(post("/trainings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(badTrainingDTO)))
                .andExpect(status().isBadRequest());
    }
}