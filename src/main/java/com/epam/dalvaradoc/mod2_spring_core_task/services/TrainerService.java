package com.epam.dalvaradoc.mod2_spring_core_task.services;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainer;
import com.epam.dalvaradoc.mod2_spring_core_task.dao.TrainingType;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainerMapper;
import com.epam.dalvaradoc.mod2_spring_core_task.repositories.TrainerRepository;
import com.epam.dalvaradoc.mod2_spring_core_task.utils.UserUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TrainerService {
  private TrainerRepository trainerRepository; 
  private final TrainerMapper mapper = new TrainerMapper();

  public Trainer getTrainerById(String userId) {
    return Optional.ofNullable(userId).map(trainerRepository::findById).get().map(Trainer::new).orElse(null);
  }

  public Trainer createTrainer(String firstName, String lastName, TrainingType specialization){
    String username = UserUtils.createUsername(firstName, lastName, trainerRepository);
    String password = UserUtils.getSaltString();
    
    Trainer trainer = new Trainer();
    trainer.setFirstName(firstName);
    trainer.setLastName(lastName);
    trainer.setUsername(username);
    trainer.setPassword(password);
    trainer.setActive(true);
    trainer.setSpecialization(specialization);

    trainerRepository.save(trainer);
    LOGGER.info("Trainer created: " + trainer.getUserId() + " " + trainer.getFirstName() + " " + trainer.getLastName());
    return trainer;
  }

  public boolean updateTrainer(Trainer trainer){
    Optional<Trainer> trainerOptional = trainerRepository.findById(trainer.getUserId());
    if (trainerOptional.isEmpty()){
      return false;
    }

    Trainer oldTrainer = trainerOptional.get();
    if (!oldTrainer.getFirstName().equals(trainer.getFirstName()) || !oldTrainer.getLastName().equals(trainer.getLastName())){
      String newUsername = UserUtils.createUsername(trainer.getFirstName(), trainer.getLastName(), trainerRepository);
      trainer.setUsername(newUsername);
      LOGGER.info("Username changed");
    }

    trainerRepository.save(trainer);
    LOGGER.info("Trainer updated: " + trainer.getUserId() + " " + trainer.getFirstName() + " " + trainer.getLastName());
    return true;
  }

  @Autowired
  public void setTrainerRepository(TrainerRepository trainerRepository) {
    this.trainerRepository = trainerRepository;
  }
}
