package com.epam.dalvaradoc.mod2_spring_core_task.services;

import java.sql.Date;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainee;
import com.epam.dalvaradoc.mod2_spring_core_task.utils.UserUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TraineeService {
  Map<String, Trainee> traineesMap;

  public Trainee getTraineeById(String id) {
    return Optional.ofNullable(id).map(traineesMap::get).map(Trainee::new).orElse(null);
  }

  public Trainee createTrainee(String firstName, String lastName, String address, Date birthdate){
    String username = UserUtils.createUsername(firstName, lastName, traineesMap);
    String password = UserUtils.getSaltString();
    
    Trainee trainee = new Trainee(firstName, lastName, username, password, true, String.valueOf(traineesMap.size()+1), birthdate, address);
    traineesMap.put(String.valueOf(traineesMap.size()+1), trainee);
    return trainee;
  }

  public boolean updateTrainee(Trainee trainee){
    if(!Optional.ofNullable(trainee).map(o -> o.getUserId()).map(traineesMap::get).isPresent()){
      return false;
    }

    Trainee oldTrainee = traineesMap.get(trainee.getUserId());
    if (!oldTrainee.getFirstName().equals(trainee.getFirstName()) || !oldTrainee.getLastName().equals(trainee.getLastName())){
      String newUsername = UserUtils.createUsername(trainee.getFirstName(), trainee.getLastName(), traineesMap);
      trainee.setUsername(newUsername);
      LOGGER.info("Username changed");
    }
    traineesMap.put(trainee.getUserId(), trainee);
    return true;
  }

  public void deleteTraineeById(String userId) {
    traineesMap.remove(userId);
  }

  @Autowired
  public void setTraineesMap(Map<String, Trainee> traineesMap) {
    this.traineesMap = traineesMap;
  }
}
