package com.epam.dalvaradoc.mod2_spring_core_task.dto;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.TrainingType;
import com.epam.dalvaradoc.mod2_spring_core_task.utils.Mapper;

public class TrainingTypeMapper implements Mapper<TrainingType, TrainingTypeDTO> {
  @Override
  public TrainingType toObject(TrainingTypeDTO dto) {
    TrainingType trainingType = new TrainingType();
    trainingType.setTrainingTypeId(dto.getTrainingTypeId());
    trainingType.setName(dto.getName());
    return trainingType;
  }

  @Override
  public TrainingTypeDTO toDTO(TrainingType object) {
    return TrainingTypeDTO.builder()
        .trainingTypeId(object.getTrainingTypeId())
        .name(object.getName())
        .build();
  }
}
