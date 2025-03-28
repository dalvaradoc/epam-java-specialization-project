package com.epam.dalvaradoc.mod2_spring_core_task.services;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.Training;
import com.epam.dalvaradoc.mod2_spring_core_task.dao.TrainingType;

@Service
public class TrainingService {
  @Autowired
  Map<String, Training> trainingMap;

  public Training getTrainingByName(String name) {
    return trainingMap.get(name);
  }

  public List<Training> getTrainings() {
    return trainingMap.values().stream().collect(Collectors.toList());
  }

  public List<Training> getTrainings(String traineeId, String trainerId, String name, TrainingType type, Date date, Integer duration) {
    return trainingMap.values().stream().filter(training -> 
      (traineeId == null || traineeId.equals(training.getTraineeId())) &&
      (trainerId == null || trainerId.equals(training.getTrainerId())) &&
      (name == null || name.equals(training.getName())) &&
      (type == null || type.equals(training.getType())) &&
      (date == null || date.equals(training.getDate())) &&
      (duration == null || duration.equals(training.getDuration()))
    ).collect(Collectors.toList());
  }

  public Training createTraining(String traineeId, String trainerId, String name, TrainingType type, Date date, Integer duration) {
    Training training = new Training(traineeId, trainerId, name, type, date, duration);
    trainingMap.put(name, training);
    return training;
  }
}
