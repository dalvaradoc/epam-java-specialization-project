package com.epam.dalvaradoc.mod2_spring_core_task.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.epam.dalvaradoc.mod2_spring_core_task.aop.CheckCredentials;
import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainee;
import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainer;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.AuthenticationDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.repositories.TraineeRepository;
import com.epam.dalvaradoc.mod2_spring_core_task.repositories.TrainerRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Service
@Validated
public class UserService {
  private TraineeRepository traineeRepository;
  private TrainerRepository trainerRepository;

  @Autowired
  public UserService(TraineeRepository traineeRepository, TrainerRepository trainerRepository) {
    this.traineeRepository = traineeRepository;
    this.trainerRepository = trainerRepository;
  }

  @CheckCredentials
  public void login(@Valid AuthenticationDTO auth) {
    //If the login not successful an exception will be thrown
  }

  @CheckCredentials
  public AuthenticationDTO changePassword(@NotNull @NotEmpty String newPassword, @Valid AuthenticationDTO auth) {
    Trainee trainee = traineeRepository.findByUsername(auth.getUsername());
    if (trainee != null){
      trainee.setPassword(newPassword);
      traineeRepository.save(trainee);
      return new AuthenticationDTO(auth.getUsername(), newPassword);
    }

    Trainer trainer = trainerRepository.findByUsername(auth.getUsername());
    if (trainer != null){
      trainer.setPassword(newPassword);
      trainerRepository.save(trainer);
      return new AuthenticationDTO(auth.getUsername(), newPassword);
    }

    return null;
  }
}
