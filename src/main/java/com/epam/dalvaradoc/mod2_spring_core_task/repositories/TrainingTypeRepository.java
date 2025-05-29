/* (C)2025 */
package com.epam.dalvaradoc.mod2_spring_core_task.repositories;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.TrainingType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingTypeRepository extends JpaRepository<TrainingType, String> {
    TrainingType findByName(String name);
}
