package com.epam.dalvaradoc.mod2_spring_core_task.services;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.Training;
import com.epam.dalvaradoc.mod2_spring_core_task.dao.TrainingType;

@Service
public class TrainingService {
  private Map<String, Training> trainingMap;

  public Training getTrainingByName(String name) {
    return trainingMap.get(name);
  }

  public List<Training> getTrainings() {
    return trainingMap.values().stream().collect(Collectors.toList());
  }

  public List<Training> getTrainings(String traineeId, String trainerId, String name, TrainingType type, Date date, Integer duration) {
    return trainingMap.values().stream().filter(training -> 
      checkTraineeId(training, traineeId) &&
      checkTrainerId(training, trainerId) &&
      checkName(training, name) &&
      checkType(training, type) &&
      checkDate(training, date) &&
      checkDuration(training, duration)
    ).collect(Collectors.toList());
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
    return Optional.ofNullable(duration).filter((d) -> d.equals(training.getDuration())).isPresent() || duration == null;
  }

  public Training createTraining(String traineeId, String trainerId, String name, TrainingType type, Date date, Integer duration) {
    Training training = new Training(traineeId, trainerId, name, type, date, duration);
    trainingMap.put(name, training);
    return training;
  }

  @Autowired
  public void setTrainingMap(Map<String, Training> trainingMap) {
    this.trainingMap = trainingMap;
  }
}
