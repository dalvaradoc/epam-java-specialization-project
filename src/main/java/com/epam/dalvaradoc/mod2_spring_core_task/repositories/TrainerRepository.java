/* (C)2025 */
package com.epam.dalvaradoc.mod2_spring_core_task.repositories;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainerRepository extends JpaRepository<Trainer, String> {
    Trainer findByUsername(String username);

    void deleteByUsername(String username);
}
