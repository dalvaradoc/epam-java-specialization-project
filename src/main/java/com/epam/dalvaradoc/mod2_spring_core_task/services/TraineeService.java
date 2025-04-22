package com.epam.dalvaradoc.mod2_spring_core_task.services;

import java.lang.foreign.Linker.Option;
import java.sql.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.dalvaradoc.mod2_spring_core_task.aop.CheckCredentials;
import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainee;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TraineeDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TraineeMapper;
import com.epam.dalvaradoc.mod2_spring_core_task.repositories.TraineeRepository;
import com.epam.dalvaradoc.mod2_spring_core_task.utils.UserUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TraineeService {
  private TraineeRepository traineeRepository;
  private UserUtils userUtils;
  private final TraineeMapper mapper = new TraineeMapper();

  @Autowired
  public TraineeService(TraineeRepository traineeRepository, UserUtils userUtils) {
    this.traineeRepository = traineeRepository;
    this.userUtils = userUtils;
  }

  @CheckCredentials
  public TraineeDTO getTraineeById(String id, String username, String password) {
    return Optional.ofNullable(id)
        .map(traineeRepository::findById)
        .map(opt -> opt.orElse(null))
        .map(mapper::toDTO)
        .orElse(null);
  }

  @CheckCredentials
  public TraineeDTO getTraineeByUsername(String username, String password) {
    return Optional.ofNullable(username)
        .map(traineeRepository::findByUsername)
        .map(mapper::toDTO)
        .orElse(null);
  }

  public TraineeDTO createTrainee(String firstName, String lastName, String address, Date birthdate){
    String username = userUtils.createUsername(firstName, lastName);
    String password = UserUtils.getSaltString();
    
    Trainee trainee = new Trainee();
    trainee.setFirstName(firstName);
    trainee.setLastName(lastName);
    trainee.setUsername(username);
    trainee.setPassword(password);
    trainee.setActive(true);
    trainee.setAddress(address);
    trainee.setBirthdate(birthdate);

    traineeRepository.save(trainee);
    return mapper.toDTO(trainee);
  }

  @CheckCredentials
  public boolean updateTrainee(Trainee trainee){
    Optional<Trainee> traineeOptional = traineeRepository.findById(trainee.getUserId());

    if(traineeOptional.isEmpty()){
      return false;
    }

    Trainee oldTrainee = traineeOptional.get();
    if (!oldTrainee.getFirstName().equals(trainee.getFirstName()) || !oldTrainee.getLastName().equals(trainee.getLastName())){
      String newUsername = userUtils.createUsername(trainee.getFirstName(), trainee.getLastName());
      trainee.setUsername(newUsername);
      LOGGER.info("Username changed");
    }
    traineeRepository.save(trainee);
    LOGGER.info("Trainee updated: " + trainee.toString());
    return true;
  }

  @CheckCredentials
  public boolean changePassword(String newPassword, String username, String password) {
    Trainee trainee = traineeRepository.findByUsername(username);
    if (trainee == null){
      return false;
    }

    trainee.setPassword(newPassword);
    traineeRepository.save(trainee);
    LOGGER.info("Trainee password changed: " + trainee.toString());
    return true;
  }

  @CheckCredentials
  public boolean changeActiveState(String userId, String username, String password) {
    Optional<Trainee> traineeOptional = traineeRepository.findById(userId);
    if (traineeOptional.isEmpty()){
      return false;
    }
    Trainee trainee = traineeOptional.get();
    trainee.setActive(!trainee.isActive());
    traineeRepository.save(trainee);
    LOGGER.info("Trainee active state changed: " + trainee.toString());
    return trainee.isActive();
  }

  @CheckCredentials
  public void deleteTraineeById(String userId, String username, String password) {
    traineeRepository.deleteById(userId);
    LOGGER.info("Trainee deleted: " + userId);
  }

  @CheckCredentials
  public void deleteTraineeByUsername(String username, String password) {
    traineeRepository.deleteByUsername(username);
    LOGGER.info("Trainee deleted: " + username);
  }
}
