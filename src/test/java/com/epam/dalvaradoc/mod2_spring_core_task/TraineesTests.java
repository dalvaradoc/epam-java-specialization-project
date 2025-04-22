package com.epam.dalvaradoc.mod2_spring_core_task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainee;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TraineeDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TraineeMapper;
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

	private final TraineeMapper traineeMapper = new TraineeMapper();

	@Test
	void traineesMapInitializedTest() {
		assertNotNull(traineeService);
		assertNotNull(traineeRepository);
		assertEquals(25, traineeRepository.findAll().size());
	}

	@Test
	void createTraineeTest() {
		TraineeDTO trainee = traineeService.createTrainee("John", "Smith", "CL 1 # 1-2", Date.valueOf("2000-02-02"));
		assertNotNull(traineeService.getTraineeById(trainee.getUserId(), trainee.getUsername(), trainee.getPassword()));
		assertEquals(traineeMapper.toDTO(traineeRepository.findById(trainee.getUserId()).get()), trainee);

		traineeRepository.deleteById(trainee.getUserId());
	}

	@Test
	void getTraineeByIdTest() {
		assertNotNull(traineeService.getTraineeById("1", "Peggie.Barthelemy", "rD4=ob.G8"));
		assertNull(traineeService.getTraineeById("300", "Peggie.Barthelemy", "rD4=ob.G8"));
	}

	@Test
	void updateTraineeTest(){
		Trainee trainee = traineeRepository.findById("1").orElse(null);
		assertNotNull(trainee);
		trainee.setAddress("CL 1 # 1-2");
		trainee.setFirstName("Jhonnn");
		trainee.setLastName("Smithhh");
		Trainee traineeCopy = new Trainee(trainee);
		traineeService.updateTrainee(trainee); // The update changes the username
		
		assertNotEquals(trainee, traineeCopy);
		assertEquals("Jhonnn.Smithhh", traineeRepository.findById("1").map(Trainee::getUsername).orElse(null));
	}

	@Test
	void deleteTraineeTest() {
		Trainee trainee = traineeMapper.toObject(traineeService.createTrainee("x", "x", "CL X # X - X", Date.valueOf("2000-02-02")));
		assertNotNull(trainee);
		traineeService.deleteTraineeById(trainee.getUserId(), trainee.getUsername(), trainee.getPassword());
		assertFalse(traineeRepository.findById(trainee.getUserId()).isPresent());
	}
}
