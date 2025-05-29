/* (C)2025 */
package com.epam.dalvaradoc.mod2_spring_core_task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainer;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.AuthenticationDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainerDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainerMapper;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainingTypeMapper;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.UpdateTrainerDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.errors.BadCredentialsException;
import com.epam.dalvaradoc.mod2_spring_core_task.repositories.TrainerRepository;
import com.epam.dalvaradoc.mod2_spring_core_task.repositories.TrainingTypeRepository;
import com.epam.dalvaradoc.mod2_spring_core_task.services.TrainerService;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TrainersTests {
    @Autowired private TrainerRepository trainerRepository;
    @Autowired private TrainingTypeRepository trainingTypeRepository;
    @Autowired private TrainerService trainerService;

    private TrainerMapper trainerMapper = new TrainerMapper();
    private TrainingTypeMapper trainingTypeMapper = new TrainingTypeMapper();

    @Test
    void contextLoads() {
        assertNotNull(trainerService);
        assertNotNull(trainerRepository);
    }

    @Test
    void trainersMapInitializedTest() {
        assertEquals(8, trainerRepository.findAll().size());
        assertNotNull(trainerRepository.findById("26"));
    }

    @Test
    void getTrainerByUsernameTest() {
        Trainer trainer = trainerRepository.findById("26").orElse(null);
        assertNotNull(trainer);
        AuthenticationDTO auth =
                new AuthenticationDTO(trainer.getUsername(), trainer.getPassword());
        TrainerDTO serviceTrainer = trainerService.getTrainerByUsername(auth);
        serviceTrainer.setAuth(auth);
        serviceTrainer.getAuth().setPassword(null); // Dtos dont have password
        assertTrainerEquals(serviceTrainer, trainerMapper.toDTO(trainer));
        assertThrows(
                BadCredentialsException.class,
                () ->
                        trainerService.getTrainerByUsername(
                                new AuthenticationDTO(trainer.getUsername(), "notThePassword")));
    }

    private void assertTrainerEquals(Trainer trainer1, Trainer trainer2) {
        assertEquals(trainer1.getUserId(), trainer2.getUserId());
        assertEquals(trainer1.getFirstName(), trainer2.getFirstName());
        assertEquals(trainer1.getLastName(), trainer2.getLastName());
        assertEquals(trainer1.getUsername(), trainer2.getUsername());
        assertEquals(trainer1.getPassword(), trainer2.getPassword());
        assertEquals(trainer1.isActive(), trainer2.isActive());
        assertEquals(
                trainer1.getSpecialization().getTrainingTypeId(),
                trainer2.getSpecialization().getTrainingTypeId());
    }

    private void assertTrainerEquals(TrainerDTO trainer1, TrainerDTO trainer2) {
        assertEquals(trainer1.getFirstName(), trainer2.getFirstName());
        assertEquals(trainer1.getLastName(), trainer2.getLastName());
        assertEquals(trainer1.getAuth().getUsername(), trainer2.getAuth().getUsername());
        assertEquals(trainer1.getAuth().getPassword(), trainer2.getAuth().getPassword());
        assertEquals(trainer1.getIsActive(), trainer2.getIsActive());
        assertEquals(
                trainer1.getSpecialization().getName(), trainer2.getSpecialization().getName());
    }

    @Test
    void createTrainerTest() {
        AuthenticationDTO auth = trainerService.createTrainer("Diego", "Alvarado", "AEROBIC");
        Trainer trainer = trainerRepository.findByUsername(auth.getUsername());
        assertNotNull(trainer);
        assertEquals("Diego.Alvarado", trainer.getUsername());

        AuthenticationDTO auth2 = trainerService.createTrainer("Diego", "Alvarado", "AEROBIC");
        Trainer trainer2 = trainerRepository.findByUsername(auth2.getUsername());
        assertNotNull(trainer2);
        assertNotEquals(trainer, trainer2);
        assertEquals("Diego.Alvarado#2", trainer2.getUsername());

        trainerRepository.deleteById(trainer.getUserId());
        trainerRepository.deleteById(trainer2.getUserId());
    }

    @Test
    void createBadTrainerTest() {
        assertThrows(
                ConstraintViolationException.class,
                () -> trainerService.createTrainer("Diego", "Alvaradosdasdj343843", "AEROBIC"));
        assertThrows(
                ConstraintViolationException.class,
                () -> trainerService.createTrainer("Diegox.#94", "Alvarado", "AEROBIC"));
    }

    @Test
    void updateTrainerTest() {
        Trainer trainer = trainerRepository.findById("26").orElse(null);
        assertNotNull(trainer);
        UpdateTrainerDTO updateTrainerDTO =
                UpdateTrainerDTO.builder()
                        .firstName("JhonnnTrainer")
                        .lastName(trainer.getLastName())
                        .isActive(trainer.isActive())
                        .specialization(trainer.getSpecialization().getName())
                        .auth(new AuthenticationDTO(trainer.getUsername(), trainer.getPassword()))
                        .build();
        // The update changes the username
        TrainerDTO trainerDTO =
                trainerService.updateTrainer(
                        updateTrainerDTO,
                        new AuthenticationDTO(trainer.getUsername(), trainer.getPassword()));

        trainerDTO.setAuth(
                new AuthenticationDTO(trainer.getUsername(), null)); // Dtos dont have password
        trainer.setFirstName("JhonnnTrainer");

        assertEquals(trainerMapper.toDTO(trainer), trainerDTO);
    }

    @Test
    void changeActiveStateTest() {
        Trainer trainer = trainerRepository.findById("26").orElse(null);
        assertNotNull(trainer);
        trainer.setActive(false);
        AuthenticationDTO auth =
                new AuthenticationDTO(trainer.getUsername(), trainer.getPassword());
        assertFalse(trainer.isActive());
        trainerService.changeActiveState(true, auth);
        assertTrue(trainerRepository.findById(trainer.getUserId()).get().isActive());
        assertThrows(
                BadCredentialsException.class,
                () ->
                        trainerService.changeActiveState(
                                false,
                                new AuthenticationDTO(trainer.getUsername(), "notThePassword")));
    }

    @Test
    void changePasswordTest() {
        Trainer trainer = trainerRepository.findById("26").orElse(null);
        assertNotNull(trainer);
        String oldPassword = trainer.getPassword();
        trainerService.changePassword("newPassword", trainer.getUsername(), trainer.getPassword());
        assertNotEquals(
                oldPassword,
                trainerRepository.findById("26").map(Trainer::getPassword).orElse(null));
    }

    // @Test
    // void deleteTrainerTest() {
    // 	TrainerDTO trainer = trainerService.createTrainer("Diego", "Alvarado",
    // trainingTypeRepository.findByName("AEROBIC"));
    // 	assertNotNull(trainer);
    // 	trainerService.deleteTrainerByUsername(trainer.getUsername(), trainer.getPassword());
    // 	assertFalse(trainerRepository.findById(trainer.getUserId()).isPresent());
    // }
}
