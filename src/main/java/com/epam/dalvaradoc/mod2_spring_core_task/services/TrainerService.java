package com.epam.dalvaradoc.mod2_spring_core_task.services;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainer;
import com.epam.dalvaradoc.mod2_spring_core_task.dao.TrainingType;
import com.epam.dalvaradoc.mod2_spring_core_task.utils.UserUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TrainerService {
  Map<String, Trainer> trainersMap;

  public Trainer getTrainerById(String userId) {
    return Optional.ofNullable(userId).map(trainersMap::get).map(Trainer::new).orElse(null);
  }

  public Trainer createTrainer(String firstName, String lastName, TrainingType specialization){
    String username = UserUtils.createUsername(firstName, lastName, trainersMap);
    String password = UserUtils.getSaltString();
    
    Trainer trainer = new Trainer(firstName, lastName, username, password, true, String.valueOf(trainersMap.size()+1), specialization);
    trainersMap.put(String.valueOf(trainersMap.size()+1), trainer);
    return trainer;
  }

  public boolean updateTrainer(Trainer trainer){
    if (!trainersMap.containsKey(trainer.getUserId())) return false;
    Trainer oldTrainer = trainersMap.get(trainer.getUserId());
    if (!oldTrainer.getFirstName().equals(trainer.getFirstName()) || !oldTrainer.getLastName().equals(trainer.getLastName())){
      String newUsername = UserUtils.createUsername(trainer.getFirstName(), trainer.getLastName(), trainersMap);
      trainer.setUsername(newUsername);
      LOGGER.info("Username changed");
    }
    trainersMap.put(trainer.getUserId(), trainer);
    return true;
  }

  @Autowired
  public void setTrainersMap(Map<String, Trainer> trainersMap) {
    this.trainersMap = trainersMap;
  }
}
