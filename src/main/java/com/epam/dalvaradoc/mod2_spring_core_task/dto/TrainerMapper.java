package com.epam.dalvaradoc.mod2_spring_core_task.dto;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainer;
import com.epam.dalvaradoc.mod2_spring_core_task.utils.Mapper;

public class TrainerMapper implements Mapper<Trainer, TrainerDTO> {

  @Override
  public Trainer toObject(TrainerDTO dto) {
    Trainer trainer = new Trainer();

    trainer.setUserId(dto.getUserId());
    trainer.setFirstName(dto.getFirstName());
    trainer.setLastName(dto.getLastName());
    trainer.setUsername(dto.getUsername());
    trainer.setPassword(dto.getPassword());
    trainer.setActive(dto.isActive());
    trainer.setSpecialization(dto.getSpecialization());

    return trainer;
  }

  @Override
  public TrainerDTO toDTO(Trainer object) {
    TrainerDTO trainerDTO = TrainerDTO.builder()
        .userId(object.getUserId())
        .firstName(object.getFirstName())
        .lastName(object.getLastName())
        .username(object.getUsername())
        .password(object.getPassword())
        .isActive(object.isActive())
        .specialization(object.getSpecialization())
        .build();

    return trainerDTO;
  }
}
