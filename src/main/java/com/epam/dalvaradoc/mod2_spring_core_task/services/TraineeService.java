package com.epam.dalvaradoc.mod2_spring_core_task.services;

import java.sql.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainee;
import com.epam.dalvaradoc.mod2_spring_core_task.utils.UserUtils;

@Service
public class TraineeService {
  @Autowired
  Map<String, Trainee> traineesMap;

  private UserUtils userUtils;

  public Trainee getTraineeById(String id) {
    return traineesMap.get(id);
  }

  public Trainee createTrainee(String firstName, String lastName, String address, Date birthdate){
    String username = userUtils.getUsername(firstName, lastName, traineesMap);
    String password = userUtils.getSaltString();
    
    Trainee trainee = new Trainee(firstName, lastName, username, password, true, String.valueOf(traineesMap.size()+1), birthdate, address);
    traineesMap.put(String.valueOf(traineesMap.size()+1), trainee);
    return trainee;
  }

  public boolean updateTrainee(Trainee trainee){
    if (!traineesMap.containsKey(trainee.getUserId())) return false;
    traineesMap.put(trainee.getUserId(), trainee);
    return true;
  }

  public void deleteTraineeById(String userId) {
    traineesMap.remove(userId);
  }

}
