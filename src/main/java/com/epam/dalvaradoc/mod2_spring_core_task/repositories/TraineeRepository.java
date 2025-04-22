package com.epam.dalvaradoc.mod2_spring_core_task.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainee;

public interface TraineeRepository extends JpaRepository<Trainee, String> {
}