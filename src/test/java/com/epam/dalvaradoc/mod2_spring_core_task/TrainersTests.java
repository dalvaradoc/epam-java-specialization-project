package com.epam.dalvaradoc.mod2_spring_core_task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
class TrainersTests {
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
		Trainer trainer = trainerRepository.findById("26").orElse(null);
		assertNotNull(trainer);
		TrainerDTO serviceTrainer = trainerService.getTrainerById(trainer.getUserId(), trainer.getUsername(), trainer.getPassword());
		assertTrainerEquals(trainerMapper.toObject(serviceTrainer), trainer);
	}

	@Test
	void getTrainerByUsernameTest() {
		Trainer trainer = trainerRepository.findById("26").orElse(null);
		assertNotNull(trainer);
		TrainerDTO serviceTrainer = trainerService.getTrainerByUsername(trainer.getUsername(), trainer.getPassword());
		assertTrainerEquals(trainerMapper.toObject(serviceTrainer), trainer);
		assertThrows(SecurityException.class, () -> trainerService.getTrainerByUsername("xxxxxx", "notThePassword"));
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
		assertTrainerEquals(trainerMapper.toObject(trainer2), trainerRepository.findById(trainer2.getUserId()).orElse(null));
		assertNotEquals(trainer, trainer2);
		assertEquals("Diego.Alvarado#2", trainer2.getUsername());

		trainerRepository.deleteById(trainer.getUserId());
		trainerRepository.deleteById(trainer2.getUserId());
	}

	@Test
	void updateTrainerTest() {
		Trainer trainer = trainerRepository.findById("26").orElse(null);
		assertNotNull(trainer);
		trainer.setSpecialization(trainingTypeRepository.findByName("FLEXIBILITY"));
		trainer.setFirstName("JhonnnTrainer");
		trainer.setLastName("SmithhhTrainer");
		// The update changes the username
		assertEquals(true, trainerService.updateTrainer(trainer));

		assertEquals("JhonnnTrainer.SmithhhTrainer", trainerService.getTrainerById("26", trainer.getUsername(), trainer.getPassword()).getUsername());
	}

	@Test
	void changeActiveStateTest() {
		Trainer trainer = trainerRepository.findById("26").orElse(null);
		assertNotNull(trainer);
		trainer.setActive(false);
		trainerService.updateTrainer(trainer);
		assertFalse(trainer.isActive());
		trainerService.changeActiveState(trainer.getUserId(), trainer.getUsername(), trainer.getPassword());
		assertTrue(trainerRepository.findById(trainer.getUserId()).get().isActive());
		assertThrows(SecurityException.class, () -> trainerService.changeActiveState(trainer.getUserId(), trainer.getUsername(), "NotThePassword"));
	}

	@Test
	void changePasswordTest() {
		Trainer trainer = trainerRepository.findById("26").orElse(null);
		assertNotNull(trainer);
		String oldPassword = trainer.getPassword();
		trainerService.changePassword("newPassword", trainer.getUsername(), trainer.getPassword());
		assertNotEquals(oldPassword, trainerRepository.findById("26").map(Trainer::getPassword).orElse(null));
	}

	// @Test
	// void deleteTrainerTest() {
	// 	TrainerDTO trainer = trainerService.createTrainer("Diego", "Alvarado", trainingTypeRepository.findByName("AEROBIC"));
	// 	assertNotNull(trainer);
	// 	trainerService.deleteTrainerByUsername(trainer.getUsername(), trainer.getPassword());
	// 	assertFalse(trainerRepository.findById(trainer.getUserId()).isPresent());
	// }
}
