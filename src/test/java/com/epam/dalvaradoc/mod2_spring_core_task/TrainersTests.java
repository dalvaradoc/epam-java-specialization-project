package com.epam.dalvaradoc.mod2_spring_core_task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainer;
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
		TrainerDTO serviceTrainer = trainerService.getTrainerById("26");

		assertTrainerEquals(trainerMapper.toObject(serviceTrainer), trainer);
	}

	private void assertTrainerEquals(Trainer trainer1, Trainer trainer2) {
		assertEquals(trainer1.getUserId(), trainer2.getUserId());
		assertEquals(trainer1.getFirstName(), trainer2.getFirstName());
		assertEquals(trainer1.getLastName(), trainer2.getLastName());
		assertEquals(trainer1.getUsername(), trainer2.getUsername());
		assertEquals(trainer1.getPassword(), trainer2.getPassword());
		assertEquals(trainer1.isActive(), trainer2.isActive());
		assertEquals(trainer1.getSpecialization().getTrainingTypeId(), trainer2.getSpecialization().getTrainingTypeId());
	}

	private void assertTrainerEquals(TrainerDTO trainer1, TrainerDTO trainer2) {
		assertEquals(trainer1.getUserId(), trainer2.getUserId());
		assertEquals(trainer1.getFirstName(), trainer2.getFirstName());
		assertEquals(trainer1.getLastName(), trainer2.getLastName());
		assertEquals(trainer1.getUsername(), trainer2.getUsername());
		assertEquals(trainer1.getPassword(), trainer2.getPassword());
		assertEquals(trainer1.isActive(), trainer2.isActive());
		assertEquals(trainer1.getSpecialization().getTrainingTypeId(), trainer2.getSpecialization().getTrainingTypeId());
	}

	@Test
	void createTrainerTest() {
		TrainerDTO trainer = trainerService.createTrainer("Diego", "Alvarado", trainingTypeRepository.findByName("AEROBIC"));
		assertNotNull(trainer);
		assertTrainerEquals(trainerMapper.toObject(trainer), trainerRepository.findById(trainer.getUserId()).orElse(null));
		assertEquals("Diego.Alvarado", trainer.getUsername());

		TrainerDTO trainer2 = trainerService.createTrainer("Diego", "Alvarado", trainingTypeRepository.findByName("AEROBIC"));
		assertNotNull(trainer2);
		assertTrainerEquals(trainer2, trainerService.getTrainerById(trainer2.getUserId()));
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
		// The update changes the username
		assertEquals(true, trainerService.updateTrainer(trainerMapper.toObject(trainer)));

		assertEquals("Jhonnn.Smithhh", trainerService.getTrainerById("26").getUsername());
	}
}
