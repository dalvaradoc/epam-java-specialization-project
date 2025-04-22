package com.epam.dalvaradoc.mod2_spring_core_task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.Date;
// import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainee;
import com.epam.dalvaradoc.mod2_spring_core_task.repositories.TraineeRepository;
import com.epam.dalvaradoc.mod2_spring_core_task.services.TraineeService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class TraineesTests {
	@Autowired
	private TraineeRepository traineeRepository;
	@Autowired
	private TraineeService traineeService;

	@Test
	void traineesMapInitializedTest() {
		assertNotNull(traineeService);
		assertNotNull(traineeRepository);
		assertEquals(25, traineeRepository.findAll().size());
	}

	@Test
	void createTraineeTest() {
		traineeService.createTrainee("John", "Smith", "CL 1 # 1-2", Date.valueOf("2000-02-02"));
	}

	@Test
	public void getTraineeByIdTest() {
		assertEquals(trainee, traineeService.getTraineeById("1"));
		// assertNull(traineesMap.get("300"));
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
