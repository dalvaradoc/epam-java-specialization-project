package com.epam.dalvaradoc.mod2_spring_core_task;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainer;
import com.epam.dalvaradoc.mod2_spring_core_task.services.TrainerService;

@SpringBootTest
public class TrainersTest {
	@Autowired
	Map<String, Trainer> trainersMap;
	@Autowired
	private TrainerService trainerService;

	@Test
	void contextLoads() {
		assertNotNull(trainerService);
		assertNotNull(trainersMap);
	}

	@Test
	public void trainersMapInitializedTest() {
		assertNotNull(trainersMap.get("1"));
	}

	@Test
	public void getTrainerByIdTest() {
		Trainer trainer = trainersMap.get("1");
		assertEquals(trainer, trainerService.getTrainerById("1"));
		assertNull(trainersMap.get("300"));
	}
}
