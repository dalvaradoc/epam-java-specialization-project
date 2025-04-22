package com.epam.dalvaradoc.mod2_spring_core_task.services;

import java.sql.Date;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
  private final TraineeMapper mapper = new TraineeMapper();

  public TraineeDTO getTraineeById(String id) {
    return Optional.ofNullable(id)
        .map(traineeRepository::findById)
        .get()
        .map(mapper::toDTO)
        .orElse(null);
  }

  public TraineeDTO createTrainee(String firstName, String lastName, String address, Date birthdate){
    String username = UserUtils.createUsername(firstName, lastName, traineeRepository);
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

  public boolean updateTrainee(Trainee trainee){
    Optional<Trainee> traineeOptional = traineeRepository.findById(trainee.getUserId());

    if(traineeOptional.isEmpty()){
      return false;
    }

    Trainee oldTrainee = traineeOptional.get();
    if (!oldTrainee.getFirstName().equals(trainee.getFirstName()) || !oldTrainee.getLastName().equals(trainee.getLastName())){
      String newUsername = UserUtils.createUsername(trainee.getFirstName(), trainee.getLastName(), traineeRepository);
      trainee.setUsername(newUsername);
      LOGGER.info("Username changed");
    }
    traineeRepository.save(trainee);
    LOGGER.info("Trainee updated: " + trainee.toString());
    return true;
  }

  public void deleteTraineeById(String userId) {
    traineeRepository.deleteById(userId);
    LOGGER.info("Trainee deleted: " + userId);
  }

  @Autowired
  public void setTraineeRepository(TraineeRepository traineeRepository) {
    this.traineeRepository = traineeRepository;
  }
}
