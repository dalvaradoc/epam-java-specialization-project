package com.epam.dalvaradoc.mod2_spring_core_task.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainer;
import com.epam.dalvaradoc.mod2_spring_core_task.dao.TrainingType;
import com.epam.dalvaradoc.mod2_spring_core_task.utils.UserUtils;

@Service
public class TrainerService {
  @Autowired
  Map<String, Trainer> trainersMap;

  private UserUtils userUtils;

  public Trainer getTrainerById(String userId) {
    return trainersMap.get(userId);
  }

  public Trainer createTrainer(String firstName, String lastName, TrainingType specialization){
    String username = userUtils.getUsername(firstName, lastName, trainersMap);
    String password = userUtils.getSaltString();
    
    Trainer trainer = new Trainer(firstName, lastName, username, password, true, String.valueOf(trainersMap.size()+1), specialization);
    trainersMap.put(String.valueOf(trainersMap.size()+1), trainer);
    return trainer;
  }

  public boolean updateTrainer(Trainer trainer){
    if (!trainersMap.containsKey(trainer.getUserId())) return false;
    trainersMap.put(trainer.getUserId(), trainer);
    return true;
  }
}
