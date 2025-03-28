package com.epam.dalvaradoc.mod2_spring_core_task.services;

import java.sql.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainee;
import com.epam.dalvaradoc.mod2_spring_core_task.utils.UserUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TraineeService {
  @Autowired
  Map<String, Trainee> traineesMap;

  private final UserUtils userUtils = new UserUtils();

  public Trainee getTraineeById(String id) {
    if (traineesMap.get(id) == null) return null;
    return new Trainee(traineesMap.get(id));
  }

  public Trainee createTrainee(String firstName, String lastName, String address, Date birthdate){
    String username = userUtils.createUsername(firstName, lastName, traineesMap);
    String password = userUtils.getSaltString();
    
    Trainee trainee = new Trainee(firstName, lastName, username, password, true, String.valueOf(traineesMap.size()+1), birthdate, address);
    traineesMap.put(String.valueOf(traineesMap.size()+1), trainee);
    return trainee;
  }

  public boolean updateTrainee(Trainee trainee){
    if (!traineesMap.containsKey(trainee.getUserId())) return false;
    Trainee oldTrainee = traineesMap.get(trainee.getUserId());
    if (!oldTrainee.getFirstName().equals(trainee.getFirstName()) || !oldTrainee.getLastName().equals(trainee.getLastName())){
      String newUsername = userUtils.createUsername(trainee.getFirstName(), trainee.getLastName(), traineesMap);
      trainee.setUsername(newUsername);
      LOGGER.info("Username changed");
    }
    traineesMap.put(trainee.getUserId(), trainee);
    return true;
  }

  public void deleteTraineeById(String userId) {
    traineesMap.remove(userId);
  }
}
