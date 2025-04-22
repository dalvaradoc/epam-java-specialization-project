package com.epam.dalvaradoc.mod2_spring_core_task.services;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainee;
import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainer;
import com.epam.dalvaradoc.mod2_spring_core_task.dao.Training;
import com.epam.dalvaradoc.mod2_spring_core_task.dao.TrainingType;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainingDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainingMapper;
import com.epam.dalvaradoc.mod2_spring_core_task.repositories.TrainingRepository;

@Service
public class TrainingService {
  private TrainingRepository trainingRepository;
  private final TrainingMapper mapper = new TrainingMapper();

  @Autowired
  public TrainingService(TrainingRepository trainingRepository) {
    this.trainingRepository = trainingRepository;
  }

  public TrainingDTO getTrainingByName(String name) {
    return trainingRepository.findByName(name).map(mapper::toDTO).orElse(null);
  }

  public TrainingDTO getTrainingById(String id) {
    return trainingRepository.findById(id).map(mapper::toDTO).orElse(null);
  }

  public List<TrainingDTO> getTrainings() {
    return trainingRepository.findAll().stream().map(mapper::toDTO).toList();
  }

  public List<TrainingDTO> getTrainingsByTraineeUsername(String traineeUsername) {
    return Optional.ofNullable(traineeUsername)
        .map(trainingRepository::findAllByTraineeUsername)
        .map(list -> list.stream().map(mapper::toDTO).toList())
        .orElse(null);
  }

  public List<TrainingDTO> getTrainingsByTraineeUsername(String traineeUsername, Date from, Date to, String trainerName, TrainingType type) {
    return Optional.ofNullable(traineeUsername)
        .map(username -> trainingRepository.findAllByTraineeUsername(traineeUsername, from, to, trainerName, type))
        .map(list -> list.stream().map(mapper::toDTO).toList())
        .orElse(null);
  }

  public List<TrainingDTO> getTrainingsByTrainerUsername(String trainerUsername) {
    return Optional.ofNullable(trainerUsername)
        .map(trainingRepository::findAllByTrainerUsername)
        .map(list -> list.stream().map(mapper::toDTO).toList())
        .orElse(null);
  }

  public List<TrainingDTO> getTrainingsByTrainerUsername(String trainerUsername, Date from, Date to, String traineeName) {
    return Optional.ofNullable(trainerUsername)
        .map(username -> trainingRepository.findAllByTrainerUsername(trainerUsername, from, to, traineeName))
        .map(list -> list.stream().map(mapper::toDTO).toList())
        .orElse(null);
  }

  public TrainingDTO createTraining(Trainee trainee, Trainer trainer, String name, TrainingType type, Date date, Integer duration) {
    TrainingDTO trainingDTO = TrainingDTO.builder()
        .trainee(trainee)
        .trainer(trainer)
        .name(name)
        .type(type)
        .date(date)
        .duration(duration)
        .build();

    Training training = trainingRepository.save(mapper.toObject(trainingDTO));
    return mapper.toDTO(training);
  }
}
