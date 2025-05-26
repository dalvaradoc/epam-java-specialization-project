package com.epam.dalvaradoc.mod2_spring_core_task.dao;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity(name = "training_types")
public class TrainingType {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long trainingTypeId;
  private String name;

  public TrainingType(Long trainingTypeId, String name) {
    this.trainingTypeId = trainingTypeId;
    this.name = name;
  }

  @OneToMany(mappedBy = "specialization", fetch = FetchType.LAZY)
  private List<Trainer> trainer = new ArrayList<>();

  @OneToMany(mappedBy = "type", fetch = FetchType.LAZY)
  private List<Training> training = new ArrayList<>();
}
