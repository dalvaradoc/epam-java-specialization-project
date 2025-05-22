package com.epam.dalvaradoc.mod2_spring_core_task.repositories;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.Training;
import com.epam.dalvaradoc.mod2_spring_core_task.dao.TrainingType;

public interface TrainingRepository extends CrudRepository<Training, String> {
  List<Training> findAll();
  Optional<Training> findByName(String name);
  void deleteByName(String name);

  @Query("SELECT t FROM trainings t WHERE t.trainee.username = :username")
  List<Training> findAllByTraineeUsername(String username);
  @Query("SELECT t FROM trainings t WHERE t.trainee.username = :username AND t.date BETWEEN :from AND :to AND t.trainer.username = :trainerName AND t.type = :type")
  List<Training> findAllByTraineeUsername(String username, Date from, Date to, String trainerName, TrainingType type);

  @Query("SELECT t FROM trainings t WHERE t.trainer.username = :username")
  List<Training> findAllByTrainerUsername(String username);
  @Query("SELECT t FROM trainings t WHERE t.trainer.username = :username AND t.date BETWEEN :from AND :to AND t.trainee.username = :traineeName")
  List<Training> findAllByTrainerUsername(String username, Date from, Date to, String traineeName);
}
