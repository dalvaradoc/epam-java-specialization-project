package com.epam.dalvaradoc.mod2_spring_core_task.dto;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainer;
import com.epam.dalvaradoc.mod2_spring_core_task.dao.Training;
import com.epam.dalvaradoc.mod2_spring_core_task.dao.TrainingType;
import com.epam.dalvaradoc.mod2_spring_core_task.utils.Mapper;

public class TrainerMapper implements Mapper<Trainer, TrainerDTO> {
  private final TrainingTypeMapper trainingTypeMapper = new TrainingTypeMapper();

  @Override
  public Trainer toObject(TrainerDTO dto) {
    Trainer trainer = new Trainer();
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
        .firstName(object.getFirstName())
        .lastName(object.getLastName())
        .isActive(object.isActive())
        .specialization(trainingTypeMapper.toDTO(object.getSpecialization()))
        .auth(AuthenticationDTO.builder()
            .username(object.getUsername())
            .build())
        .trainees(object.getTrainings()
            .stream()
            .map(Training::getTrainee)
            .map(trainee -> TraineeDTO.builder()
                .auth(AuthenticationDTO.builder()
                    .username(trainee.getUsername())
                    .build())
                .firstName(trainee.getFirstName())
                .lastName(trainee.getLastName())
                .build())
            .toList())
        .build();
  }
}
