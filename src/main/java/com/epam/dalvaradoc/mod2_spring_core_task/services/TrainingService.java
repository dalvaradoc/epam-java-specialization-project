package com.epam.dalvaradoc.mod2_spring_core_task.services;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.hibernate.annotations.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.epam.dalvaradoc.mod2_spring_core_task.aop.CheckCredentials;
import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainee;
import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainer;
import com.epam.dalvaradoc.mod2_spring_core_task.dao.Training;
import com.epam.dalvaradoc.mod2_spring_core_task.dao.TrainingType;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.AuthenticationDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TraineeMapper;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainerMapper;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainingDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainingMapper;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainingTypeDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainingTypeMapper;
import com.epam.dalvaradoc.mod2_spring_core_task.errors.BadCredentialsException;
import com.epam.dalvaradoc.mod2_spring_core_task.repositories.TraineeRepository;
import com.epam.dalvaradoc.mod2_spring_core_task.repositories.TrainerRepository;
import com.epam.dalvaradoc.mod2_spring_core_task.repositories.TrainingRepository;
import com.epam.dalvaradoc.mod2_spring_core_task.repositories.TrainingTypeRepository;
import com.epam.dalvaradoc.mod2_spring_core_task.validations.UsernameConstraint;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Service
@Validated
public class TrainingService {
  private TrainingRepository trainingRepository;
  private TrainingTypeRepository trainingTypeRepository;
  private TraineeRepository traineeRepository;
  private TrainerRepository trainerRepository;

  private final TrainingMapper mapper = new TrainingMapper();
  private final TrainingTypeMapper trainingTypeMapper = new TrainingTypeMapper();

  @Autowired
  public TrainingService(TrainingRepository trainingRepository, TrainingTypeRepository trainingTypeRepository,
      TraineeRepository traineeRepository, TrainerRepository trainerRepository) {
    this.trainingRepository = trainingRepository;
    this.trainingTypeRepository = trainingTypeRepository;
    this.traineeRepository = traineeRepository;
    this.trainerRepository = trainerRepository;
  }

  public TrainingDTO getTrainingByName(@NotNull String name) {
    return trainingRepository.findByName(name).map(mapper::toDTO).orElse(null);
  }

  public TrainingDTO getTrainingById(@NotNull String id) {
    return trainingRepository.findById(id).map(mapper::toDTO).orElse(null);
  }

  public List<TrainingDTO> getTrainings() {
    return trainingRepository.findAll().stream().map(mapper::toDTO).toList();
  }

  public List<TrainingDTO> getTrainingsByTraineeUsername(@UsernameConstraint String traineeUsername) {
    return Optional.ofNullable(traineeUsername)
        .map(trainingRepository::findAllByTraineeUsername)
        .map(list -> list.stream().map(mapper::toDTO).toList())
        .orElse(null);
  }

  public List<TrainingDTO> getTrainingsByTraineeUsername(@UsernameConstraint String traineeUsername, Date from, Date to, @UsernameConstraint String trainerUsername, @NotNull TrainingType type) {
    return Optional.ofNullable(traineeUsername)
        .map(username -> trainingRepository.findAllByTraineeUsername(traineeUsername, from, to, trainerUsername, type))
        .map(list -> list.stream().map(mapper::toDTO).toList())
        .orElse(null);
  }

  public List<TrainingDTO> getTrainingsByTrainerUsername(@UsernameConstraint String trainerUsername) {
    return Optional.ofNullable(trainerUsername)
        .map(trainingRepository::findAllByTrainerUsername)
        .map(list -> list.stream().map(mapper::toDTO).toList())
        .orElse(null);
  }

  public List<TrainingDTO> getTrainingsByTrainerUsername(@UsernameConstraint String trainerUsername, Date from, Date to, @UsernameConstraint String traineeUsername) {
    return Optional.ofNullable(trainerUsername) .map(username -> trainingRepository.findAllByTrainerUsername(trainerUsername, from, to, traineeUsername))
        .map(list -> list.stream().map(mapper::toDTO).toList())
        .orElse(null);
  }

  @CheckCredentials
  public TrainingDTO createTraining(@Valid TrainingDTO dto, @Valid AuthenticationDTO auth) {
    Trainee trainee = traineeRepository.findByUsername(auth.getUsername());
    Trainer trainer = trainerRepository.findByUsername(auth.getUsername());

    if (dto.getName() == null && dto.getName().isEmpty()) {
      throw new IllegalArgumentException("Training name cannot be null or empty");
    }

    if (trainee == null && trainer == null) {
      throw new IllegalArgumentException("User not found");
    }

    if (trainee != null && dto.getTrainee().getAuth() != null && !auth.getUsername().equals(dto.getTrainee().getAuth().getUsername())) {
      throw new BadCredentialsException("Trainee username does not match");
    }
    if (trainer != null && dto.getTrainer().getAuth() != null && !auth.getUsername().equals(dto.getTrainer().getAuth().getUsername())) {
      throw new BadCredentialsException("Trainee username does not match");
    }

    trainee = trainee == null ? traineeRepository.findByUsername(dto.getTrainee().getAuth().getUsername()) : trainee;
    trainer = trainer == null ? trainerRepository.findByUsername(dto.getTrainer().getAuth().getUsername()) : trainer;

    if (trainee == null || trainer == null) {
      throw new IllegalArgumentException("User not found");
    }

    Training training = new Training();
    training.setTrainee(trainee);
    training.setTrainer(trainer);
    training.setName(dto.getName());
    training.setType(trainingTypeRepository.findByName(dto.getType().getName()));
    training.setDate(dto.getDate());
    training.setDuration(dto.getDuration());
    
    trainingRepository.save(training);
    return mapper.toDTO(training);
  }


  @CheckCredentials
  public List<TrainingTypeDTO> getAllTrainingTypes(@Valid AuthenticationDTO auth) {
    return trainingTypeRepository.findAll().stream().map(trainingTypeMapper::toDTO).toList();
  }
}
