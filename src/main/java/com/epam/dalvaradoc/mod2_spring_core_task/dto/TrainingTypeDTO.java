package com.epam.dalvaradoc.mod2_spring_core_task.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TrainingTypeDTO {
  private Long trainingTypeId;
  private String name;

  public TrainingTypeDTO(Long trainingTypeId, String name) {
    this.trainingTypeId = trainingTypeId;
    this.name = name;
  }
}
