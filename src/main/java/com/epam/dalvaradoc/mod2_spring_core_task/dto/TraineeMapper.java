package com.epam.dalvaradoc.mod2_spring_core_task.dto;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainee;
import com.epam.dalvaradoc.mod2_spring_core_task.utils.Mapper;

public class TraineeMapper implements Mapper<Trainee, TraineeDTO> {

  @Override
  public Trainee toObject(TraineeDTO dto) {
    Trainee trainee = new Trainee();

    trainee.setUserId(dto.getUserId());
    trainee.setFirstName(dto.getFirstName());
    trainee.setLastName(dto.getLastName());
    trainee.setUsername(dto.getUsername());
    trainee.setPassword(dto.getPassword());
    trainee.setActive(dto.isActive());
    trainee.setBirthdate(dto.getBirthdate());
    trainee.setAddress(dto.getAddress());

    return trainee;
  }

  @Override
  public TraineeDTO toDTO(Trainee object) {
    TraineeDTO traineeDTO = TraineeDTO.builder()
        .userId(object.getUserId())
        .firstName(object.getFirstName())
        .lastName(object.getLastName())
        .username(object.getUsername())
        .password(object.getPassword())
        .isActive(object.isActive())
        .birthdate(object.getBirthdate())
        .address(object.getAddress())
        .build();

    return traineeDTO;
  }
}
