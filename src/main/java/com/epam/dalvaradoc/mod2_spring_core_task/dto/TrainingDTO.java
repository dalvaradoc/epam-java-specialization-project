package com.epam.dalvaradoc.mod2_spring_core_task.dto;

import java.sql.Date;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.TrainingType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TrainingDTO {
  private String trainingId;
  private String traineeId;
  private String trainerId;
  private String name;
  private TrainingType type;
  private Date date;
  private int duration;
}
