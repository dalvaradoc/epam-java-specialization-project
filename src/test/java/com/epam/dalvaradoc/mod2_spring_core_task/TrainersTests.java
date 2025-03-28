package com.epam.dalvaradoc.mod2_spring_core_task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainer;
import com.epam.dalvaradoc.mod2_spring_core_task.dao.TrainingType;
import com.epam.dalvaradoc.mod2_spring_core_task.services.TrainerService;

@SpringBootTest
public class TrainersTests {
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
		assertEquals(trainersMap.size(), 8);
		assertNotNull(trainersMap.get("1"));
	}

	@Test
	public void getTrainerByIdTest() {
		Trainer trainer = trainersMap.get("1");
		assertEquals(trainer, trainerService.getTrainerById("1"));
		assertNull(trainersMap.get("300"));
	}

	@Test
	void createTrainerTest() {
		Trainer trainer = trainerService.createTrainer("Diego", "Alvarado", TrainingType.AEROBIC);
		assertNotNull(trainer);
		assertEquals(trainer, trainerService.getTrainerById(trainer.getUserId()));
		assertEquals(trainer.getUsername(), "Diego.Alvarado");

		Trainer trainer2 = trainerService.createTrainer("Diego", "Alvarado", TrainingType.AEROBIC);
		assertNotNull(trainer2);
		assertEquals(trainer2, trainerService.getTrainerById(trainer2.getUserId()));
		assertNotEquals(trainer, trainer2);
		assertEquals(trainer2.getUsername(), "Diego.Alvarado#2");

		trainersMap.remove(trainer.getUserId());
		trainersMap.remove(trainer2.getUserId());
	}

	@Test
	void updateTrainerTest() {
		Trainer trainer = trainerService.getTrainerById("1");
		trainer.setSpecialization(TrainingType.FLEXIBILITY);
		trainer.setFirstName("Jhonnn");
		trainer.setLastName("Smithhh");
		Trainer trainerCopy = new Trainer(trainer);
		trainerService.updateTrainer(trainer); // The update changes the username

		assertNotEquals(trainer, trainerCopy);
		assertEquals(trainerService.getTrainerById("1").getUsername(), "Jhonnn.Smithhh");
	}
}
