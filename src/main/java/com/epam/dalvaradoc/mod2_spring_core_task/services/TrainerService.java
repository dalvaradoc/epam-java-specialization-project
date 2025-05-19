package com.epam.dalvaradoc.mod2_spring_core_task.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.epam.dalvaradoc.mod2_spring_core_task.aop.CheckCredentials;
import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainer;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainerDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainerMapper;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainingTypeDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainingTypeMapper;
import com.epam.dalvaradoc.mod2_spring_core_task.repositories.TrainerRepository;
import com.epam.dalvaradoc.mod2_spring_core_task.utils.UserUtils;
import com.epam.dalvaradoc.mod2_spring_core_task.validations.NameLikeStringConstraint;
import com.epam.dalvaradoc.mod2_spring_core_task.validations.UsernameConstraint;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Validated
public class TrainerService {
  private TrainerRepository trainerRepository; 

  private UserUtils userUtils;
  private final TrainerMapper mapper = new TrainerMapper();
  private final TrainingTypeMapper trainingTypeMapper = new TrainingTypeMapper();

  @Autowired
  public TrainerService(TrainerRepository trainerRepository, UserUtils userUtils) {
    this.trainerRepository = trainerRepository;
    this.userUtils = userUtils;
  }

  public List<TrainerDTO> getAllTrainers() {
    return trainerRepository.findAll()
        .stream()
        .map(mapper::toDTO)
        .toList();
  }

  @CheckCredentials
  public TrainerDTO getTrainerById(@NotNull String userId, @UsernameConstraint String username, @NotNull String password) {
    return Optional.ofNullable(userId)
        .map(trainerRepository::findById)
        .flatMap(opt -> opt)
        .map(mapper::toDTO)
        .orElse(null);
  }

  @CheckCredentials
  public TrainerDTO getTrainerByUsername(@UsernameConstraint String username, @NotNull String password) {
    return Optional.ofNullable(username)
        .map(trainerRepository::findByUsername)
        .map(mapper::toDTO)
        .orElse(null);
  }

  public TrainerDTO createTrainer(@NameLikeStringConstraint String firstName, @NameLikeStringConstraint String lastName, @NotNull TrainingTypeDTO specialization){
    String username = userUtils.createUsername(firstName, lastName);
    String password = UserUtils.getSaltString();
    
    Trainer trainer = new Trainer();
    trainer.setFirstName(firstName);
    trainer.setLastName(lastName);
    trainer.setUsername(username);
    trainer.setPassword(password);
    trainer.setActive(true);
    trainer.setSpecialization(trainingTypeMapper.toObject(specialization));

    trainerRepository.save(trainer);
    LOGGER.info("Trainer created: " + trainer.getUserId() + " " + trainer.getFirstName() + " " + trainer.getLastName());
    return mapper.toDTO(trainer);
  }

  @CheckCredentials
  public boolean updateTrainer(@Valid @NotNull Trainer trainer){
    Optional<Trainer> trainerOptional = trainerRepository.findById(trainer.getUserId());
    if (trainerOptional.isEmpty()){
      return false;
    }

    Trainer oldTrainer = trainerOptional.get();
    if (!oldTrainer.getFirstName().equals(trainer.getFirstName()) || !oldTrainer.getLastName().equals(trainer.getLastName())){
      String newUsername = userUtils.createUsername(trainer.getFirstName(), trainer.getLastName());
      trainer.setUsername(newUsername);
      LOGGER.info("Username changed");
    }

    trainerRepository.save(trainer);
    LOGGER.info("Trainer updated: " + trainer.getUserId() + " " + trainer.getFirstName() + " " + trainer.getLastName());
    return true;
  }

  @CheckCredentials
  public boolean changeActiveState(@NotNull String userId, @UsernameConstraint String username, @NotNull String password) {
    Optional<Trainer> trainerOptional = trainerRepository.findById(userId);
    if (trainerOptional.isEmpty()){
      return false;
    }
    Trainer trainer = trainerOptional.get();
    trainer.setActive(!trainer.isActive());
    trainerRepository.save(trainer);
    LOGGER.info("Trainer active state changed: " + trainer.getUserId() + " " + trainer.getFirstName() + " " + trainer.getLastName());
    return trainer.isActive();
  }

  @CheckCredentials
  public boolean changePassword(@NotNull String newPassword, @UsernameConstraint String username, @NotNull String password) {
    Trainer trainer = trainerRepository.findByUsername(username);
    if (trainer == null){
      return false;
    }

    trainer.setPassword(newPassword);
    trainerRepository.save(trainer);
    LOGGER.info("Trainer password changed: " + trainer.getUserId() + " " + trainer.getFirstName() + " " + trainer.getLastName());
    return true;
  }
}
