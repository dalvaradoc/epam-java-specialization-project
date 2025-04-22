package com.epam.dalvaradoc.mod2_spring_core_task.dao;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "trainings")
public class Training {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String trainingId;
  @ManyToOne
  @JoinColumn(name = "TRAINEE_ID")
  private Trainee trainee;
  @ManyToOne
  @JoinColumn(name = "TRAINER_ID")
  private Trainer trainer;
  private String name;
  @ManyToOne
  @JoinColumn(name = "TYPE")
  private TrainingType type;
  private Date date;
  private int duration;

  public Training(Training training){
    this.trainee = training.getTrainee();
    this.trainer = training.getTrainer();
    this.name = training.getName();
    this.type = training.getType();
    this.date = training.date;
    this.duration = training.duration;
  }

  @Override
  public String toString() {
    return "Training [trainingId=" + trainingId + ", traineeId=" + trainee + ", trainerId=" + trainer + ", name="
        + name + ", type=" + type + ", date=" + date + ", duration=" + duration + "]";
  }
}
