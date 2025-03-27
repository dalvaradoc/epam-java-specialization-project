package com.epam.dalvaradoc.mod2_spring_core_task.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainee;

@Service
public class TraineeService {
  @Autowired
  Map<String, Trainee> traineesMap;

  public Trainee getTraineeById(String id) {
    return traineesMap.get(id);
  }
}
