package com.epam.dalvaradoc.mod2_spring_core_task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainee;
import com.epam.dalvaradoc.mod2_spring_core_task.services.TraineeService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class Mod2SpringCoreTaskApplicationTests {
	@Autowired
	Map<String, Trainee> traineesMap;
	@Autowired
	private TraineeService traineeService;

	@Test
	void contextLoads() {
		assertNotNull(traineeService);
		assertNotNull(traineesMap);
	}

	@Test
	public void traineesMapInitializedTest() {
		assertNotNull(traineesMap.get("1"));
	}

	@Test
	public void getTraineeByIdTest() {
		Trainee trainee = traineesMap.get("1");
		assertEquals(trainee, traineeService.getTraineeById("1"));
		assertNull(traineesMap.get("300"));
	}
}
