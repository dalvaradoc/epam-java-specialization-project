package com.epam.dalvaradoc.mod2_spring_core_task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainee;
import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainer;
import com.epam.dalvaradoc.mod2_spring_core_task.dao.Training;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.AuthenticationDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TraineeDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TraineeMapper;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainerDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainerMapper;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainingDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainingTypeDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.errors.BadCredentialsException;
import com.epam.dalvaradoc.mod2_spring_core_task.repositories.TrainingRepository;
import com.epam.dalvaradoc.mod2_spring_core_task.repositories.TrainingTypeRepository;
import com.epam.dalvaradoc.mod2_spring_core_task.services.TrainingService;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;

@SpringBootTest
class TrainingTests {
  @Autowired
  TrainingService trainingService;
  @Autowired
  TrainingRepository trainingRepository;
  @Autowired
  TrainingTypeRepository trainingTypeRepository;

  private final TraineeMapper traineeMapper = new TraineeMapper();
  private final TrainerMapper trainerMapper = new TrainerMapper();
  private final AuthenticationDTO auth = new AuthenticationDTO("Faunie.Hauck", "password");

  @Test
	void contextLoads() {
		assertNotNull(trainingService);
		assertNotNull(trainingRepository);
	}

  @Test
  void isInitializing() {
    assertEquals(50, trainingRepository.findAll().size());
    assertTrue(trainingRepository.findByName("at turpis a").isPresent());
  }

  @Test
  void getTrainingByNameTest() {
    assertNotNull(trainingService.getTrainingByName("at turpis a"));
    assertNull(trainingService.getTrainingByName("xxxxxx"));
  }

  @Test
  void getTrainingsByTraineeNameTest() {
    assertEquals(2, trainingService.getTrainingsByTraineeUsername("Lenard.Pedgrift").size());
    assertEquals(0, trainingService.getTrainingsByTraineeUsername("xxxxxx.xxxx#4").size());

    assertEquals(1, trainingService.getTrainingsByTraineeUsername("Lenard.Pedgrift", Date.valueOf("2025-01-01"), Date.valueOf("2026-12-31"), "Nobie.Linney", trainingTypeRepository.findByName("PLYOMETRICS")).size());
  }

  @Test
  void getTrainingsByTrainerNameTest() {
    assertEquals(7, trainingService.getTrainingsByTrainerUsername("Stafford.Cicco").size());
    assertEquals(0, trainingService.getTrainingsByTrainerUsername("xxxxxx.xxxx").size());

    assertEquals(1, trainingService.getTrainingsByTrainerUsername("Stafford.Cicco", Date.valueOf("2025-01-01"), Date.valueOf("2026-12-31"), "Cobby.Castagneri").size());
  }

  @Test
  @Transactional
  void createTrainingTest() {
    Trainee traineeEntity = trainingRepository.findAll().get(0).getTrainee();
    AuthenticationDTO traineeAuth = new AuthenticationDTO(traineeEntity.getUsername(), traineeEntity.getPassword());
    TraineeDTO trainee = traineeMapper.toDTO(traineeEntity);
    trainee.setAuth(traineeAuth);

    Trainer trainerEntity = trainingRepository.findAll().get(0).getTrainer();
    AuthenticationDTO trainerAuth = new AuthenticationDTO(trainerEntity.getUsername(), trainerEntity.getPassword());
    TrainerDTO trainer = trainerMapper.toDTO(trainerEntity);
    trainer.setAuth(trainerAuth);

    TrainingDTO trainingDTO = TrainingDTO.builder()
        .name("Nombre Cualquiera")
        .type(new TrainingTypeDTO(6L, "CARDIO"))
        .date(Date.valueOf("2025-04-21"))
        .duration(10)
        .trainee(trainee)
        .trainer(trainer)
        .build();

    // Using the tests auth it will throw a BadCredentialsException because the
    // auth is not from the trainee nor the trainer
    assertThrows(BadCredentialsException.class, () -> trainingService.createTraining(trainingDTO, auth));
    TrainingDTO training = trainingService.createTraining(trainingDTO, traineeAuth);
    assertNotNull(training);

    Training entityTraining = trainingRepository.findByName(training.getName()).orElseThrow(() -> new IllegalArgumentException(training.getName()));

    assertEquals(training.getName(), entityTraining.getName());
    assertEquals(training.getTrainee().getAuth().getUsername(), entityTraining.getTrainee().getUsername());
    assertEquals(training.getTrainer().getAuth().getUsername(), entityTraining.getTrainer().getUsername());
    assertEquals(training.getType().getName(), entityTraining.getType().getName());
    assertEquals(training.getDate(), entityTraining.getDate());
    assertEquals(training.getDuration(), entityTraining.getDuration());  

    trainingRepository.deleteById(entityTraining.getTrainingId());
  }

  @Test
  void createBadTrainingTest() {
    Trainee trainee = trainingRepository.findAll().get(0).getTrainee();
    Trainer trainer = trainingRepository.findAll().get(0).getTrainer();

    TrainingDTO trainingDTO = TrainingDTO.builder()
        .name("Nombre Cualquiera")
        .type(new TrainingTypeDTO(6L, "CARDIO"))
        .date(Date.valueOf("2025-04-21"))
        .duration(10)
        .trainee(traineeMapper.toDTO(trainee))
        .trainer(trainerMapper.toDTO(trainer))
        .build();

    assertThrows(ConstraintViolationException.class, () -> trainingService.createTraining(trainingDTO.toBuilder().name(null).build(), auth));
    assertThrows(ConstraintViolationException.class, () -> trainingService.createTraining(trainingDTO.toBuilder().trainer(null).build(), auth));
    assertThrows(ConstraintViolationException.class, () -> trainingService.createTraining(trainingDTO.toBuilder().trainee(null).build(), auth));
    assertThrows(ConstraintViolationException.class, () -> trainingService.createTraining(trainingDTO.toBuilder().type(null).build(), auth));
    assertThrows(ConstraintViolationException.class, () -> trainingService.createTraining(trainingDTO.toBuilder().duration(-1).build(), auth));
  }

}
