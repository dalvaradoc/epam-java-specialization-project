package com.epam.dalvaradoc.mod2_spring_core_task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.Date;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainee;
import com.epam.dalvaradoc.mod2_spring_core_task.services.TraineeService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class TraineesTests {
	@Autowired
	Map<String, Trainee> traineesMap;
	@Autowired
	private TraineeService traineeService;

	@Test
	void traineesMapInitializedTest() {
		assertNotNull(traineeService);
		assertNotNull(traineesMap);
		assertEquals(25, traineesMap.size());
		assertNotNull(traineesMap.get("1"));
	}

	@Test
	public void getTraineeByIdTest() {
		Trainee trainee = traineesMap.get("1");
		assertEquals(trainee, traineeService.getTraineeById("1"));
		assertNull(traineesMap.get("300"));
	}

	@Test
	void createTraineeTest() {
		Trainee trainee = traineeService.createTrainee("Diego", "Alvarado", "CL 100 # 100 - 10", Date.valueOf("2000-01-01"));
		assertNotNull(trainee);
		assertEquals(trainee, traineeService.getTraineeById(trainee.getUserId()));
		assertEquals(trainee.getUsername(), "Diego.Alvarado");

		Trainee trainee2 = traineeService.createTrainee("Diego", "Alvarado", "CL 100 # 100 - 10", Date.valueOf("2000-01-01"));
		assertNotNull(trainee2);
		assertEquals(trainee2, traineeService.getTraineeById(trainee2.getUserId()));
		assertNotEquals(trainee, trainee2);
		assertEquals("Diego.Alvarado#2", trainee2.getUsername());

		traineesMap.remove(trainee.getUserId());
		traineesMap.remove(trainee2.getUserId());
	}

	@Test
	void updateTraineeTest(){
		Trainee trainee = traineeService.getTraineeById("1");
		trainee.setAddress("CL 1 # 1-2");
		trainee.setFirstName("Jhonnn");
		trainee.setLastName("Smithhh");
		Trainee traineeCopy = new Trainee(trainee);
		traineeService.updateTrainee(trainee); // The update changes the username
		
		assertNotEquals(trainee, traineeCopy);
		assertEquals(traineeService.getTraineeById("1").getUsername(), "Jhonnn.Smithhh");
	}

	@Test
	void deleteTraineeTest() {
		Trainee trainee = traineeService.createTrainee("x", "x", "CL X # X - X", Date.valueOf("2000-02-02"));
		assertNotNull(trainee);
		traineeService.deleteTraineeById(trainee.getUserId());
		assertNull(traineeService.getTraineeById(trainee.getUserId()));
	}
}
