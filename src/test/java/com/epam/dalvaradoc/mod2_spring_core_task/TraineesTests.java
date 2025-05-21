package com.epam.dalvaradoc.mod2_spring_core_task;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainee;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.AuthenticationDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TraineeDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.UpdateTraineeDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.errors.BadCredentialsException;
import com.epam.dalvaradoc.mod2_spring_core_task.repositories.TraineeRepository;
import com.epam.dalvaradoc.mod2_spring_core_task.services.TraineeService;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class TraineesTests {

    @Autowired
    private TraineeRepository traineeRepository;
    
    @Autowired
    private TraineeService traineeService;

    @Test
    void contextLoads() {
        assertNotNull(traineeService);
        assertNotNull(traineeRepository);
    }

    @Test
		@Transactional
    void createTraineeTest() {
        AuthenticationDTO auth = traineeService.createTrainee(
            "John", 
            "Smith", 
            "123 Main St", 
            Date.valueOf("2000-01-01")
        );
        
        assertNotNull(auth);
        assertEquals("John.Smith", auth.getUsername());
        
        Trainee trainee = traineeRepository.findByUsername(auth.getUsername());
        assertNotNull(trainee);
        assertEquals("John", trainee.getFirstName());
        assertEquals("Smith", trainee.getLastName());
        assertEquals("123 Main St", trainee.getAddress());
        
        // Cleanup
        traineeRepository.deleteByUsername(auth.getUsername());
    }

    @Test
    void createBadTraineeTest() {
        assertThrows(ConstraintViolationException.class, () -> 
            traineeService.createTrainee(
                "John123", // Invalid first name
                "Smith", 
                "123 Main St", 
                Date.valueOf("2000-01-01")
            )
        );

        assertThrows(ConstraintViolationException.class, () -> 
            traineeService.createTrainee(
                "John", 
                "Smith123", // Invalid last name
                "123 Main St", 
                Date.valueOf("2000-01-01")
            )
        );
    }

    @Test
		@Transactional
    void updateTraineeTest() {
        // Create a test trainee
        AuthenticationDTO auth = traineeService.createTrainee(
            "Alice", 
            "Brown", 
            "789 Pine St", 
            Date.valueOf("1990-03-10")
        );
        
        UpdateTraineeDTO updateDTO = UpdateTraineeDTO.builder()
            .firstName("AliceUpdated")
            .lastName("BrownUpdated")
            .address("New Address")
            .isActive(true)
            .auth(auth)
            .build();
        
        TraineeDTO updated = traineeService.updateTrainee(updateDTO, auth);
        assertNotNull(updated);
        assertEquals("AliceUpdated", updated.getFirstName());
        assertEquals("BrownUpdated", updated.getLastName());
        assertEquals("New Address", updated.getAddress());
        
        // Cleanup
        traineeRepository.deleteByUsername(auth.getUsername());
    }

    @Test
		@Transactional
    void updateTraineeWithBadCredentialsTest() {
        AuthenticationDTO auth = traineeService.createTrainee(
            "Bob", 
            "Wilson", 
            "321 Elm St", 
            Date.valueOf("1988-12-20")
        );
        
        AuthenticationDTO wrongAuth = new AuthenticationDTO(auth.getUsername(), "wrongPassword");
        
        UpdateTraineeDTO updateDTO = UpdateTraineeDTO.builder()
            .firstName("BobUpdated")
            .lastName("WilsonUpdated")
            .address("New Address")
            .isActive(true)
            .auth(wrongAuth)
            .build();
        
        assertThrows(BadCredentialsException.class, () -> 
            traineeService.updateTrainee(updateDTO, wrongAuth)
        );
        
        // Cleanup
        traineeRepository.deleteByUsername(auth.getUsername());
    }

    @Test
		@Transactional
    void changePasswordTest() {
        AuthenticationDTO auth = traineeService.createTrainee(
            "Charlie", 
            "Brown", 
            "654 Oak St", 
            Date.valueOf("1992-07-15")
        );
        
        String oldPassword = auth.getPassword();
        String newPassword = "newPassword123";
        
        assertTrue(traineeService.changePassword(newPassword, auth.getUsername(), oldPassword));
        
        // Verify old password no longer works
        assertThrows(BadCredentialsException.class, () -> 
            traineeService.getTraineeByUsername(auth)
        );
        
        // Cleanup
        traineeRepository.deleteByUsername(auth.getUsername());
    }

    @Test
		  @Transactional
    void changeActiveStateTest() {
        AuthenticationDTO auth = traineeService.createTrainee(
            "David", 
            "Miller", 
            "987 Pine St", 
            Date.valueOf("1987-09-25")
        );
        
        assertTrue(traineeService.changeActiveState(true, auth));
        assertFalse(traineeService.changeActiveState(false, auth));
        
        Trainee trainee = traineeRepository.findByUsername(auth.getUsername());
        assertFalse(trainee.isActive());
        
        // Cleanup
        traineeRepository.deleteByUsername(auth.getUsername());
    }

    @Test
    @Transactional
    void deleteTraineeTest() {
        AuthenticationDTO auth = traineeService.createTrainee(
            "Eric", 
            "Johnson", 
            "741 Maple St", 
            Date.valueOf("1995-03-15")
        );
        
        assertNotNull(traineeRepository.findByUsername(auth.getUsername()));
        
        traineeService.deleteTraineeByUsername(auth.getUsername(), auth.getPassword());
        
        assertNull(traineeRepository.findByUsername(auth.getUsername()));
    }
}