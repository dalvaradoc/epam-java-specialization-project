package com.epam.dalvaradoc.mod2_spring_core_task.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TrainingTypeDTO {
  private Long id;
  private String name;

  public TrainingTypeDTO(Long id, String name) {
    this.id = id;
    this.name = name;
  }
}
