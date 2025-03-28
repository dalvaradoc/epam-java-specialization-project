package com.epam.dalvaradoc.mod2_spring_core_task.dao;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Training {
  private String traineeId;
  private String trainerId;
  private String name;
  private TrainingType type;
  private Date date;
  private int duration;
}
