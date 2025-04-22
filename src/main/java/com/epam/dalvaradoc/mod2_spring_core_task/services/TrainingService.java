package com.epam.dalvaradoc.mod2_spring_core_task.services;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.Training;
import com.epam.dalvaradoc.mod2_spring_core_task.dao.TrainingType;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainingDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainingMapper;
import com.epam.dalvaradoc.mod2_spring_core_task.repositories.TrainingRepository;

@Service
public class TrainingService {
  private TrainingRepository trainingRepository;
  private final TrainingMapper mapper = new TrainingMapper();

  public TrainingDTO getTrainingByName(String name) {
    return trainingRepository.findByName(name).map(mapper::toDTO).orElse(null);
  }

  public List<TrainingDTO> getTrainings() {
    return trainingRepository.findAll().stream().map(mapper::toDTO).toList();
  }

  //Se podría mejorar usando queries en vez de streams
  public List<Training> getTrainings(String traineeId, String trainerId, String name, TrainingType type, Date date, Integer duration) {
    return trainingRepository.findAll().stream()
        .filter(training -> checkTraineeId(training, traineeId))
        .filter(training -> checkTrainerId(training, trainerId))
        .filter(training -> checkName(training, name))
        .filter(training -> checkType(training, type))
        .filter(training -> checkDate(training, date))
        .filter(training -> checkDuration(training, duration))
        .toList();
  }

  private boolean checkTraineeId(Training training, String traineeId){
    return Optional.ofNullable(traineeId).filter(training.getTraineeId()::equals).isPresent() || traineeId == null;
  }
  private boolean checkTrainerId(Training training, String trainerId){
    return Optional.ofNullable(trainerId).filter(training.getTrainerId()::equals).isPresent() || trainerId == null;
  }
  private boolean checkName(Training training, String name){
    return Optional.ofNullable(name).filter(training.getName()::equals).isPresent() || name == null;
  }
  private boolean checkType(Training training, TrainingType type){
    return Optional.ofNullable(type).filter(training.getType()::equals).isPresent() || type == null;
  }
  private boolean checkDate(Training training, Date date){
    return Optional.ofNullable(date).filter(training.getDate()::equals).isPresent() || date == null;
  }
  private boolean checkDuration(Training training, Integer duration){
    return Optional.ofNullable(duration).filter(d -> d.equals(training.getDuration())).isPresent() || duration == null;
  }

  public Training createTraining(String traineeId, String trainerId, String name, TrainingType type, Date date, Integer duration) {
    Training training = new Training();
    training.setTraineeId(traineeId);
    training.setTrainerId(trainerId);
    training.setName(name);
    training.setType(type);
    training.setDate(date);
    training.setDuration(duration);

    trainingRepository.save(training);
    return training;
  }

  @Autowired
  public void setTrainingRepository(TrainingRepository trainingRepository) {
    this.trainingRepository = trainingRepository;
  }
}
