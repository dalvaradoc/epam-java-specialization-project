package com.epam.dalvaradoc.mod2_spring_core_task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.Training;
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
    assertEquals(trainingMap.size(), 50);
    assertNotNull(trainingMap.get("at turpis a"));
  }
}
