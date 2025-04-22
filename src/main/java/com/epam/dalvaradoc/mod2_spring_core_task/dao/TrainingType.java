package com.epam.dalvaradoc.mod2_spring_core_task.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "training_types")
public class TrainingType {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String trainingTypeId;
  private String name;

  @OneToOne(mappedBy = "specialization")
  private Trainer trainer;
  @OneToOne(mappedBy = "type")
  private Training training;
}
