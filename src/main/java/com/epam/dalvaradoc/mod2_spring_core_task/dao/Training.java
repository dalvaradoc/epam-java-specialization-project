package com.epam.dalvaradoc.mod2_spring_core_task.dao;

import java.sql.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "trainings")
public class Training {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String trainingId;
  private String traineeId;
  private String trainerId;
  private String name;
  @ManyToOne
  @JoinColumn(name = "TYPE")
  private TrainingType type;
  private Date date;
  private int duration;

  public Training(Training training){
    this.traineeId = training.getTraineeId();
    this.trainerId = training.getTrainerId();
    this.name = training.getName();
    this.type = training.getType();
    this.date = training.date;
    this.duration = training.duration;
  }
}
