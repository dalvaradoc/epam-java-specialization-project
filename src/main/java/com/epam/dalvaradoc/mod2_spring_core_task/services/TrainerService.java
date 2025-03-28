package com.epam.dalvaradoc.mod2_spring_core_task.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainer;
import com.epam.dalvaradoc.mod2_spring_core_task.dao.TrainingType;
import com.epam.dalvaradoc.mod2_spring_core_task.utils.UserUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TrainerService {
  @Autowired
  Map<String, Trainer> trainersMap;

  private final UserUtils userUtils = new UserUtils();

  public Trainer getTrainerById(String userId) {
    if (trainersMap.get(userId) == null) return null;
    return new Trainer(trainersMap.get(userId));
  }

  public Trainer createTrainer(String firstName, String lastName, TrainingType specialization){
    String username = userUtils.createUsername(firstName, lastName, trainersMap);
    String password = userUtils.getSaltString();
    
    Trainer trainer = new Trainer(firstName, lastName, username, password, true, String.valueOf(trainersMap.size()+1), specialization);
    trainersMap.put(String.valueOf(trainersMap.size()+1), trainer);
    return trainer;
  }

  public boolean updateTrainer(Trainer trainer){
    if (!trainersMap.containsKey(trainer.getUserId())) return false;
    Trainer oldTrainer = trainersMap.get(trainer.getUserId());
    if (!oldTrainer.getFirstName().equals(trainer.getFirstName()) || !oldTrainer.getLastName().equals(trainer.getLastName())){
      String newUsername = userUtils.createUsername(trainer.getFirstName(), trainer.getLastName(), trainersMap);
      trainer.setUsername(newUsername);
      LOGGER.info("Username changed");
    }
    trainersMap.put(trainer.getUserId(), trainer);
    return true;
  }
}
