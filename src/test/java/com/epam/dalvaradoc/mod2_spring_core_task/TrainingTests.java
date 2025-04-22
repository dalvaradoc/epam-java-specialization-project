package com.epam.dalvaradoc.mod2_spring_core_task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.epam.dalvaradoc.mod2_spring_core_task.repositories.TrainingRepository;
import com.epam.dalvaradoc.mod2_spring_core_task.repositories.TrainingTypeRepository;
import com.epam.dalvaradoc.mod2_spring_core_task.services.TrainingService;

@SpringBootTest
public class TrainingTests {
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
  void getTrainingWithFiltersTest() {
    assertEquals(4, trainingService.getTrainings(null, null, null, trainingTypeRepository.findByName("AEROBIC"), null, null).size());
    assertEquals(2, trainingService.getTrainings(null, null, null, null, null, 38).size());
    assertEquals(1, trainingService.getTrainings("21","4","ut erat curabitur gravida",trainingTypeRepository.findByName("WEIGHT"), null,54).size());
    assertNotNull(trainingService.getTrainingByName("ut erat curabitur gravida"));
  }
}
