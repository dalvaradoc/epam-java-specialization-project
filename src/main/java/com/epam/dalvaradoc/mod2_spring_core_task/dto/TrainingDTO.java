package com.epam.dalvaradoc.mod2_spring_core_task.dto;

import java.sql.Date;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainee;
import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainer;
import com.epam.dalvaradoc.mod2_spring_core_task.dao.TrainingType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TrainingDTO {
  private String trainingId;
  private Trainee trainee;
  private Trainer trainer;
  private String name;
  private TrainingType type;
  private Date date;
  private int duration;
}
