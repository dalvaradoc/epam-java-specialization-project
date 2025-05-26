package com.epam.dalvaradoc.mod2_spring_core_task.services;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.epam.dalvaradoc.mod2_spring_core_task.aop.CheckCredentials;
import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainer;
import com.epam.dalvaradoc.mod2_spring_core_task.dao.Training;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.AuthenticationDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TraineeDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainerDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainerMapper;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainingDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainingMapper;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.UpdateTrainerDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.repositories.TrainerRepository;
import com.epam.dalvaradoc.mod2_spring_core_task.repositories.TrainingTypeRepository;
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
  private TrainingTypeRepository trainingTypeRepository;

  private UserUtils userUtils;
  private final TrainerMapper mapper = new TrainerMapper();
  private final TrainingMapper trainingMapper = new TrainingMapper();

  @Autowired
  public TrainerService(TrainerRepository trainerRepository, TrainingTypeRepository trainingTypeRepository,
      UserUtils userUtils) {
    this.trainerRepository = trainerRepository;
    this.trainingTypeRepository = trainingTypeRepository;
    this.userUtils = userUtils;
  }

  public List<TrainerDTO> getAllTrainers() {
    return trainerRepository.findAll()
        .stream()
        .map(mapper::toDTO)
        .toList();
  }

  @CheckCredentials
  public TrainerDTO getTrainerById(@NotNull String userId, @UsernameConstraint String username,
      @NotNull String password) {
    return Optional.ofNullable(userId)
        .map(trainerRepository::findById)
        .flatMap(opt -> opt)
        .map(mapper::toDTO)
        .orElse(null);
  }

  @CheckCredentials
  public TrainerDTO getTrainerByUsername(@Valid AuthenticationDTO auth) {
    return Optional.ofNullable(auth.getUsername())
        .map(trainerRepository::findByUsername)
        .map(mapper::toDTO)
        .map(trainer -> {
          trainer.setAuth(null);
          return trainer;
        })
        .orElse(null);
  }

  public AuthenticationDTO createTrainer(@NameLikeStringConstraint String firstName,
      @NameLikeStringConstraint String lastName, @NotNull String specialization) {
    String username = userUtils.createUsername(firstName, lastName);
    String password = UserUtils.getSaltString();

    Trainer trainer = new Trainer();
    trainer.setFirstName(firstName);
    trainer.setLastName(lastName);
    trainer.setUsername(username);
    trainer.setPassword(password);
    trainer.setActive(true);
    trainer.setSpecialization(trainingTypeRepository.findByName(specialization));

    trainerRepository.save(trainer);
    LOGGER.info("Trainer created: " + trainer.getUserId() + " " + trainer.getFirstName() + " " + trainer.getLastName());
    return new AuthenticationDTO(username, password);
  }

  @CheckCredentials
  public TrainerDTO updateTrainer(@Valid UpdateTrainerDTO dto, @Valid AuthenticationDTO auth) {
    Optional<Trainer> trainerOptional = Optional
        .ofNullable(trainerRepository.findByUsername(dto.getAuth().getUsername()));
    if (trainerOptional.isEmpty()) {
      return null;
    }

    Trainer trainer = trainerOptional.get();

    trainer.setFirstName(dto.getFirstName());
    trainer.setLastName(dto.getLastName());
    trainer.setSpecialization(trainingTypeRepository.findByName(dto.getSpecialization()));
    trainer.setActive(dto.getIsActive());

    trainerRepository.save(trainer);
    LOGGER.info("Trainer updated: " + trainer.getUserId() + " " + trainer.getFirstName() + " " + trainer.getLastName());
    return mapper.toDTO(trainer);
  }

  @CheckCredentials
  public boolean changeActiveState(boolean active, @Valid AuthenticationDTO auth) {
    Optional<Trainer> trainerOptional = Optional.ofNullable(trainerRepository.findByUsername(auth.getUsername()));
    if (trainerOptional.isEmpty()) {
      return false;
    }
    Trainer trainer = trainerOptional.get();
    trainer.setActive(active);
    trainerRepository.save(trainer);
    LOGGER.info("Trainer active state changed: " + trainer.getUsername() + trainer.isActive());
    return trainer.isActive();
  }

  @CheckCredentials
  public boolean changePassword(@NotNull String newPassword, @UsernameConstraint String username,
      @NotNull String password) {
    Trainer trainer = trainerRepository.findByUsername(username);
    if (trainer == null) {
      return false;
    }

    trainer.setPassword(newPassword);
    trainerRepository.save(trainer);
    LOGGER.info("Trainer password changed: " + trainer.getUserId() + " " + trainer.getFirstName() + " "
        + trainer.getLastName());
    return true;
  }

  @CheckCredentials
  public List<TrainingDTO> getTrainings(Map<String, Object> filters, @Valid AuthenticationDTO auth) {
    Trainer trainer = trainerRepository.findByUsername(auth.getUsername());
    if (trainer == null) {
      return List.of();
    }

    return trainer.getTrainings()
        .stream()
        .filter(training -> getTrainingFiltersPredicate(filters, training))
        .map(trainingMapper::toDTO)
        .map(dto -> {
          dto.setTrainee(TraineeDTO.builder()
              .firstName(dto.getTrainee().getFirstName())
              .lastName(dto.getTrainee().getLastName())
              .build());
          dto.setTrainer(null);
          return dto;
        })
        .toList();
  }

  private boolean getTrainingFiltersPredicate(Map<String, Object> filters, Training training) {
    if (filters.get("from") != null) {
      Date from = (Date) filters.get("from");
      if (training.getDate().before(from)) {
        return false;
      }
    }
    if (filters.get("to") != null) {
      Date to = (Date) filters.get("to");
      if (training.getDate().after(to)) {
        return false;
      }
    }
    if (filters.get("traineeName") != null) {
      String traineeName = (String) filters.get("traineeName");
      if (!training.getTrainee().getFirstName().equals(traineeName)
          && !training.getTrainee().getLastName().equals(traineeName)) {
        return false;
      }
    }
    if (filters.get("trainingType") != null) {
      String trainingType = (String) filters.get("trainingType");
      if (!training.getType().getName().equals(trainingType)) {
        return false;
      }
    }
    return true;
  }
}
