package com.epam.dalvaradoc.mod2_spring_core_task.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainer;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.AuthenticationDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainerDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainingTypeDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.UpdateTrainerDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.repositories.TrainerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
class TrainerControllerTests {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private TrainerRepository trainerRepository;

    @Test
    void contextLoads() {
        assertNotNull(mockMvc);
        assertNotNull(objectMapper);
        assertNotNull(trainerRepository);
    }

    @Test
    @Transactional
    void getTrainerByUsernameTest() throws Exception {
        Trainer trainer = trainerRepository.findById("26").orElse(null);
        assertNotNull(trainer);
        
        AuthenticationDTO auth = new AuthenticationDTO(trainer.getUsername(), trainer.getPassword());
        
        MvcResult result = mockMvc.perform(get("/trainers/{username}", trainer.getUsername())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(auth)))
            .andExpect(status().isOk())
            .andReturn();

        TrainerDTO trainerDTO = objectMapper.readValue(
            result.getResponse().getContentAsString(), 
            TrainerDTO.class
        );

        assertNotNull(trainerDTO);
        assertEquals(trainer.getFirstName(), trainerDTO.getFirstName());
        assertEquals(trainer.getLastName(), trainerDTO.getLastName());
        
        // Test invalid credentials
        auth.setPassword("wrongPassword");
        mockMvc.perform(get("/trainers/{username}", trainer.getUsername())
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(auth)))
            .andExpect(status().isUnauthorized());
    }

    @Test
    @Transactional
    void createTrainerTest() throws Exception {
        TrainerDTO trainerDTO = TrainerDTO.builder()
            .firstName("Diego")
            .lastName("Alvarado")
            .specialization(TrainingTypeDTO.builder().name("AEROBIC").build())
            .build();

        MvcResult result = mockMvc.perform(post("/trainers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(trainerDTO)))
            .andExpect(status().isOk())
            .andReturn();

        AuthenticationDTO auth = objectMapper.readValue(
            result.getResponse().getContentAsString(), 
            AuthenticationDTO.class
        );

        assertNotNull(auth);
        assertEquals("Diego.Alvarado", auth.getUsername());
        
        Trainer trainer = trainerRepository.findByUsername(auth.getUsername());
        assertNotNull(trainer);
        assertEquals("Diego", trainer.getFirstName());
        assertEquals("Alvarado", trainer.getLastName());
        assertEquals("AEROBIC", trainer.getSpecialization().getName());
        
        // Cleanup
        trainerRepository.deleteByUsername(auth.getUsername());
    }

    @Test
    void createBadTrainerTest() throws Exception {
        // Test invalid first name
        TrainerDTO badTrainerDTO1 = TrainerDTO.builder()
            .firstName("Diego123")
            .lastName("Alvarado")
            .specialization(TrainingTypeDTO.builder().name("AEROBIC").build())
            .build();

        mockMvc.perform(post("/trainers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(badTrainerDTO1)))
            .andExpect(status().isBadRequest());

        // Test invalid last name
        TrainerDTO badTrainerDTO2 = TrainerDTO.builder()
            .firstName("Diego")
            .lastName("Alvarado123")
            .specialization(TrainingTypeDTO.builder().name("AEROBIC").build())
            .build();

        mockMvc.perform(post("/trainers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(badTrainerDTO2)))
            .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void updateTrainerTest() throws Exception {
        Trainer trainer = trainerRepository.findById("26").orElse(null);
        assertNotNull(trainer);

        AuthenticationDTO auth = new AuthenticationDTO(trainer.getUsername(), trainer.getPassword());

        UpdateTrainerDTO updateTrainerDTO = UpdateTrainerDTO.builder()
            .firstName("UpdatedTrainer")
            .lastName(trainer.getLastName())
            .isActive(trainer.isActive())
            .specialization("AEROBIC")
            .auth(auth)
            .build();

        MvcResult result = mockMvc.perform(put("/trainers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateTrainerDTO)))
            .andExpect(status().isOk())
            .andReturn();

        TrainerDTO updatedTrainer = objectMapper.readValue(
            result.getResponse().getContentAsString(), 
            TrainerDTO.class
        );

        assertNotNull(updatedTrainer);
        assertEquals("UpdatedTrainer", updatedTrainer.getFirstName());
        assertEquals(trainer.getLastName(), updatedTrainer.getLastName());

        // Test invalid credentials
        auth.setPassword("wrongPassword");
        mockMvc.perform(put("/trainers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updateTrainerDTO)))
            .andExpect(status().isUnauthorized())
            .andReturn();
    }

    @Test
    @Transactional
    void changeActiveStateTest() throws Exception {
        Trainer trainer = trainerRepository.findById("26").orElse(null);
        assertNotNull(trainer);
        
        AuthenticationDTO auth = new AuthenticationDTO(trainer.getUsername(), trainer.getPassword());

        mockMvc.perform(patch("/trainers/{username}/set-active-state", trainer.getUsername())
            .param("active", "false")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(auth)))
            .andExpect(status().isOk());

        Trainer updatedTrainer = trainerRepository.findByUsername(trainer.getUsername());
        assertFalse(updatedTrainer.isActive());

        // Test with invalid credentials
        auth.setPassword("wrongPassword");
        mockMvc.perform(patch("/trainers/{username}/set-active-state", trainer.getUsername())
            .param("active", "true")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(auth)))
            .andExpect(status().isUnauthorized());
    }
}