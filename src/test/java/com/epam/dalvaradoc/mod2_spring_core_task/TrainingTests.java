package com.epam.dalvaradoc.mod2_spring_core_task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.Date;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.Training;
import com.epam.dalvaradoc.mod2_spring_core_task.dao.TrainingType;
import com.epam.dalvaradoc.mod2_spring_core_task.services.TrainingService;

@SpringBootTest
public class TrainingTests {
  @Autowired
  TrainingService trainingService;

  @Autowired
  Map<String,Training> trainingMap;

  @Test
	void contextLoads() {
		assertNotNull(trainingService);
		assertNotNull(trainingMap);
	}

  @Test
  void isInitializing() {
    assertEquals(50, trainingMap.size());
    assertNotNull(trainingMap.get("at turpis a"));
  }

  @Test
  void getTrainingByNameTest() {
    assertNotNull(trainingService.getTrainingByName("at turpis a"));
    assertNull(trainingService.getTrainingByName("xxxxxx"));
  }

  @Test
  void getTrainingWithFiltersTest() {
    assertEquals(4, trainingService.getTrainings(null, null, null, TrainingType.AEROBIC, null, null).size());
    assertEquals(2, trainingService.getTrainings(null, null, null, null, null, 38).size());
    assertEquals(1, trainingService.getTrainings("21","4","ut erat curabitur gravida",TrainingType.WEIGHT, null,54).size());
    // La fecha esta un poco rara
    // assertNull(trainingService.getTrainingByName("ut erat curabitur gravida"));
  }
}
