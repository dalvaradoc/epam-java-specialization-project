package com.epam.dalvaradoc.mod2_spring_core_task.dto;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainer;
import com.epam.dalvaradoc.mod2_spring_core_task.dao.TrainingType;
import com.epam.dalvaradoc.mod2_spring_core_task.utils.UserMapper;

public class TrainerMapper implements UserMapper<Trainer, TrainerDTO> {
  private final TrainingTypeMapper trainingTypeMapper = new TrainingTypeMapper();

  @Override
  public Trainer toObject(TrainerDTO dto) {
    Trainer trainer = new Trainer();

    trainer.setUserId(dto.getUserId());
    trainer.setFirstName(dto.getFirstName());
    trainer.setLastName(dto.getLastName());
    trainer.setUsername(dto.getAuth().getUsername());
    trainer.setPassword(dto.getAuth().getPassword());
    trainer.setActive(dto.getIsActive());
    trainer.setSpecialization(trainingTypeMapper.toObject(dto.getSpecialization()));

    return trainer;
  }

  @Override
  public TrainerDTO toDTO(Trainer object) {
    return TrainerDTO.builder()
        .userId(object.getUserId())
        .firstName(object.getFirstName())
        .lastName(object.getLastName())
        .isActive(object.isActive())
        .specialization(trainingTypeMapper.toDTO(object.getSpecialization()))
        .auth(new AuthenticationDTO(object.getUsername(), object.getPassword()))
        .build();
  }

  @Override
  public TrainerDTO toDTOwithoutPassword(Trainer object) {
    return TrainerDTO.builder()
        .userId(object.getUserId())
        .firstName(object.getFirstName())
        .lastName(object.getLastName())
        .isActive(object.isActive())
        .specialization(trainingTypeMapper.toDTO(object.getSpecialization()))
        .build();
  }
}
