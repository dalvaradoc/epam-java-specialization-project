/* (C)2025 */
package com.epam.dalvaradoc.mod2_spring_core_task.repositories;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TraineeRepository extends JpaRepository<Trainee, String> {
    Trainee findByUsername(String username);

    void deleteByUsername(String username);
}
