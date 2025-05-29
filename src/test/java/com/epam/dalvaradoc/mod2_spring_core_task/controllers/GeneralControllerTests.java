/* (C)2025 */
package com.epam.dalvaradoc.mod2_spring_core_task.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainer;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.AuthenticationDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.repositories.TrainerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
class GeneralControllerTests {

    @Autowired private MockMvc mockMvc;

    @Autowired private ObjectMapper objectMapper;

    @Autowired private TrainerRepository trainerRepository;

    @Test
    void contextLoads() {
        assertNotNull(mockMvc);
        assertNotNull(objectMapper);
    }

    @Test
    void loginSuccessTest() throws Exception {
        Trainer trainer = trainerRepository.findById("26").orElse(null);
        assertNotNull(trainer);

        AuthenticationDTO auth =
                new AuthenticationDTO(trainer.getUsername(), trainer.getPassword());

        mockMvc.perform(
                        get("/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(auth)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void loginFailTest() throws Exception {
        // Bad username format
        AuthenticationDTO auth = new AuthenticationDTO("nonexistent", "wrongpassword");

        mockMvc.perform(
                        get("/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(auth)))
                .andExpect(status().isBadRequest());

        // User doesn't exists
        auth = new AuthenticationDTO("okname.oklastname", "wrongpassword");

        mockMvc.perform(
                        get("/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(auth)))
                .andExpect(status().isUnauthorized());

        // Wrong password
        Trainer trainer = trainerRepository.findById("26").orElse(null);
        assertNotNull(trainer);

        auth = new AuthenticationDTO(trainer.getUsername(), "wrongpassword");

        mockMvc.perform(
                        get("/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(auth)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Transactional
    void changePasswordTest() throws Exception {
        Trainer trainer = trainerRepository.findById("26").orElse(null);
        assertNotNull(trainer);

        String oldPassword = trainer.getPassword();

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("username", trainer.getUsername());
        requestBody.put("password", oldPassword);
        requestBody.put("newPassword", "newTestPassword123");

        MvcResult result =
                mockMvc.perform(
                                put("/change-password")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(requestBody)))
                        .andExpect(status().isOk())
                        .andReturn();

        AuthenticationDTO response =
                objectMapper.readValue(
                        result.getResponse().getContentAsString(), AuthenticationDTO.class);

        assertNotNull(response);
        assertEquals(trainer.getUsername(), response.getUsername());

        // Verify old password no longer works
        mockMvc.perform(
                        get("/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(
                                                new AuthenticationDTO(
                                                        trainer.getUsername(), oldPassword))))
                .andExpect(status().isUnauthorized());

        // Verify new password works
        mockMvc.perform(
                        get("/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        objectMapper.writeValueAsString(
                                                new AuthenticationDTO(
                                                        trainer.getUsername(),
                                                        "newTestPassword123"))))
                .andExpect(status().isOk());
    }

    @Test
    void changePasswordFailTest() throws Exception {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("username", "nonexistent");
        requestBody.put("password", "wrongpassword");
        requestBody.put("newPassword", "newpassword");

        mockMvc.perform(
                        put("/change-password")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isUnauthorized());
    }
}
