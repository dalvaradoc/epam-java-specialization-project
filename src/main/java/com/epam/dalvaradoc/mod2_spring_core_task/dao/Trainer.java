/* (C)2025 */
package com.epam.dalvaradoc.mod2_spring_core_task.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "trainers")
public class Trainer extends User {
    @ManyToOne
    @JoinColumn(name = "SPECIALIZATION")
    private TrainingType specialization;

    @OneToMany(mappedBy = "trainer", fetch = FetchType.EAGER)
    private List<Training> trainings = new ArrayList<>();

    @ManyToMany(mappedBy = "trainers")
    private Set<Trainee> trainees = new HashSet<>();

    public Trainer(
            String firstName,
            String lastName,
            String username,
            String password,
            boolean isActive,
            String userId,
            TrainingType specialization) {
        super(userId, firstName, lastName, username, password, isActive);
        this.specialization = specialization;
    }

    public Trainer(Trainer trainer) {
        super(
                trainer.getUserId(),
                trainer.getFirstName(),
                trainer.getLastName(),
                trainer.getUsername(),
                trainer.getPassword(),
                trainer.isActive());
        this.specialization = trainer.getSpecialization();
    }
}
