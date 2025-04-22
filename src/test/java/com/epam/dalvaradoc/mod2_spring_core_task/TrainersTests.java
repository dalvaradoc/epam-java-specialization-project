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
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainerDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainerMapper;
import com.epam.dalvaradoc.mod2_spring_core_task.repositories.TrainerRepository;
import com.epam.dalvaradoc.mod2_spring_core_task.repositories.TrainingTypeRepository;
import com.epam.dalvaradoc.mod2_spring_core_task.services.TrainerService;

@SpringBootTest
public class TrainersTests {
	@Autowired
	private TrainerRepository trainerRepository;
	@Autowired
	private TrainingTypeRepository trainingTypeRepository;
	@Autowired
	private TrainerService trainerService;

	private TrainerMapper trainerMapper = new TrainerMapper();

	@Test
	void contextLoads() {
		assertNotNull(trainerService);
		assertNotNull(trainerRepository);
	}

	@Test
	void trainersMapInitializedTest() {
		assertEquals(8, trainerRepository.findAll().size());
		assertNotNull(trainerRepository.findById("26"));
	}

	@Test
	void getTrainerByIdTest() {
		Trainer trainer = trainerRepository.findById("26").get();
		assertEquals(trainerMapper.toDTO(trainer), trainerService.getTrainerById("26"));
		assertNull(trainerService.getTrainerById("300"));
	}

	@Test
	void createTrainerTest() {
		TrainerDTO trainer = trainerService.createTrainer("Diego", "Alvarado", trainingTypeRepository.findByName("AEROBIC"));
		assertNotNull(trainer);
		assertEquals(trainer, trainerMapper.toDTO(trainerRepository.findById(trainer.getUserId()).orElse(null)));
		assertEquals("Diego.Alvarado", trainer.getUsername());

		TrainerDTO trainer2 = trainerService.createTrainer("Diego", "Alvarado", trainingTypeRepository.findByName("AEROBIC"));
		assertNotNull(trainer2);
		assertEquals(trainer2, trainerService.getTrainerById(trainer2.getUserId()));
		assertNotEquals(trainer, trainer2);
		assertEquals("Diego.Alvarado#2", trainer2.getUsername());

		trainerRepository.deleteById(trainer.getUserId());
		trainerRepository.deleteById(trainer2.getUserId());
	}

	@Test
	void updateTrainerTest() {
		TrainerDTO trainer = trainerService.getTrainerById("26");
		trainer.setSpecialization(trainingTypeRepository.findByName("FLEXIBILITY"));
		trainer.setFirstName("Jhonnn");
		trainer.setLastName("Smithhh");
		Trainer trainerCopy = trainerMapper.toObject(trainer);
		// The update changes the username
		assertEquals(true, trainerService.updateTrainer(trainerMapper.toObject(trainer)));

		assertNotEquals(trainer, trainerCopy);
		assertEquals("Jhonnn.Smithhh", trainerService.getTrainerById("26").getUsername());
	}
}
