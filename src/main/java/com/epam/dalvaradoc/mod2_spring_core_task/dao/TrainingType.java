package com.epam.dalvaradoc.mod2_spring_core_task.dao;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "training_types")
public class TrainingType {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long trainingTypeId;
  private String name;

  @OneToMany(mappedBy = "specialization")
  private List<Trainer> trainer = new ArrayList<>();

  @OneToMany(mappedBy = "type")
  private List<Training> training = new ArrayList<>();
}
