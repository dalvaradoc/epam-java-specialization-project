/* (C)2025 */
package com.epam.dalvaradoc.mod2_spring_core_task.dto;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainee;
import com.epam.dalvaradoc.mod2_spring_core_task.dao.Training;
import com.epam.dalvaradoc.mod2_spring_core_task.utils.Mapper;
import java.util.stream.Collectors;

public class TraineeMapper implements Mapper<Trainee, TraineeDTO> {
    private final TrainingTypeMapper trainingTypeMapper = new TrainingTypeMapper();
    private final TrainerMapper trainerMapper = new TrainerMapper();

    @Override
    public Trainee toObject(TraineeDTO dto) {
        Trainee trainee = new Trainee();

        trainee.setFirstName(dto.getFirstName());
        trainee.setLastName(dto.getLastName());
        trainee.setActive(dto.getIsActive());
        trainee.setBirthdate(dto.getBirthdate());
        trainee.setAddress(dto.getAddress());
        trainee.setUsername(dto.getAuth().getUsername());
        trainee.setPassword(dto.getAuth().getPassword());
        trainee.setTrainers(
                dto.getTrainers().stream()
                        .map(trainerMapper::toObject)
                        .collect(Collectors.toSet()));

        return trainee;
    }

    @Override
    public TraineeDTO toDTO(Trainee object) {
        return TraineeDTO.builder()
                .firstName(object.getFirstName())
                .lastName(object.getLastName())
                .isActive(object.isActive())
                .birthdate(object.getBirthdate())
                .address(object.getAddress())
                .trainers(
                        object.getTrainings().stream()
                                .map(Training::getTrainer)
                                .map(
                                        trainer ->
                                                TrainerDTO.builder()
                                                        .auth(
                                                                AuthenticationDTO.builder()
                                                                        .username(
                                                                                trainer
                                                                                        .getUsername())
                                                                        .build())
                                                        .firstName(trainer.getFirstName())
                                                        .lastName(trainer.getLastName())
                                                        .specialization(
                                                                trainingTypeMapper.toDTO(
                                                                        trainer
                                                                                .getSpecialization()))
                                                        .build())
                                .toList())
                .build();
    }
}
