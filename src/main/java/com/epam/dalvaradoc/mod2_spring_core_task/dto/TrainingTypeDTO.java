package com.epam.dalvaradoc.mod2_spring_core_task.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TrainingTypeDTO {
  private String name;

  public TrainingTypeDTO(String name) {
    this.name = name;
  }
}
