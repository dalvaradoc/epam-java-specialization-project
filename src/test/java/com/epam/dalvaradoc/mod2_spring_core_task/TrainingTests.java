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
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainingDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.repositories.TrainingRepository;
import com.epam.dalvaradoc.mod2_spring_core_task.repositories.TrainingTypeRepository;
import com.epam.dalvaradoc.mod2_spring_core_task.services.TrainingService;

import jakarta.validation.ConstraintViolationException;

@SpringBootTest
class TrainingTests {
  @Autowired
  TrainingService trainingService;
  @Autowired
  TrainingRepository trainingRepository;
  @Autowired
  TrainingTypeRepository trainingTypeRepository;

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
  void createTrainingTest() {
    Trainee trainee = trainingRepository.findAll().get(0).getTrainee();
    Trainer trainer = trainingRepository.findAll().get(0).getTrainer();

    TrainingDTO training = trainingService.createTraining(trainee, trainer, "Nombre Cualquiera", trainingTypeRepository.findByName("CARDIO"), Date.valueOf("2025-04-21"), 10);
    assertNotNull(training);
    Training entityTraining = trainingRepository.findById(training.getTrainingId()).orElse(null);

    assertEquals(training.getName(), entityTraining.getName());
    assertEquals(training.getTrainee().getUserId(), entityTraining.getTrainee().getUserId());
    assertEquals(training.getTrainer().getUserId(), entityTraining.getTrainer().getUserId());
    assertEquals(training.getType().getName(), entityTraining.getType().getName());
    assertEquals(training.getDate(), entityTraining.getDate());
    assertEquals(training.getDuration(), entityTraining.getDuration());  

    trainingRepository.deleteById(training.getTrainingId());
  }

  @Test
  void createBadTrainingTest() {
    Trainee trainee = trainingRepository.findAll().get(0).getTrainee();
    Trainer trainer = trainingRepository.findAll().get(0).getTrainer();

    assertThrows(ConstraintViolationException.class, () -> trainingService.createTraining(null, trainer, "Nombre Cualquiera", trainingTypeRepository.findByName("CARDIO"), Date.valueOf("2025-04-21"), 10));
    assertThrows(ConstraintViolationException.class, () -> trainingService.createTraining(trainee, null, "Nombre Cualquiera", trainingTypeRepository.findByName("CARDIO"), Date.valueOf("2025-04-21"), 10));
    assertThrows(ConstraintViolationException.class, () -> trainingService.createTraining(trainee, trainer, "Nombre Cualquiera", null, Date.valueOf("2025-04-21"), 10));
    assertThrows(ConstraintViolationException.class, () -> trainingService.createTraining(trainee, trainer, "Nombre Cualquiera", trainingTypeRepository.findByName("CARDIO"), Date.valueOf("2025-04-21"), -1));
  }

}
