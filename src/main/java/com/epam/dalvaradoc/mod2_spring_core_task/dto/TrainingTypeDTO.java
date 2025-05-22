package com.epam.dalvaradoc.mod2_spring_core_task.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TrainingTypeDTO {
  private Long id;
  private String name;

  public TrainingTypeDTO(Long id, String name) {
    this.id = id;
    this.name = name;
  }
}
