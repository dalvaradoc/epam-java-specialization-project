/* (C)2025 */
package com.epam.dalvaradoc.mod2_spring_core_task.dto;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.Training;
import com.epam.dalvaradoc.mod2_spring_core_task.utils.Mapper;

public class TrainingMapper implements Mapper<Training, TrainingDTO> {
    private final TraineeMapper traineeMapper = new TraineeMapper();
    private final TrainerMapper trainerMapper = new TrainerMapper();
    private final TrainingTypeMapper trainingTypeMapper = new TrainingTypeMapper();

    @Override
    public Training toObject(TrainingDTO dto) {
        Training training = new Training();
        training.setTrainee(traineeMapper.toObject(dto.getTrainee()));
        training.setTrainer(trainerMapper.toObject(dto.getTrainer()));
        training.setName(dto.getName());
        training.setType(trainingTypeMapper.toObject(dto.getType()));
        training.setDate(dto.getDate());
        training.setDuration(dto.getDuration());
        return training;
    }

    @Override
    public TrainingDTO toDTO(Training object) {
        return TrainingDTO.builder()
                .trainee(
                        TraineeDTO.builder()
                                .firstName(object.getTrainee().getFirstName())
                                .lastName(object.getTrainee().getLastName())
                                .auth(
                                        AuthenticationDTO.builder()
                                                .username(object.getTrainee().getUsername())
                                                .build())
                                .build())
                .trainer(
                        TrainerDTO.builder()
                                .firstName(object.getTrainer().getFirstName())
                                .lastName(object.getTrainer().getLastName())
                                .auth(
                                        AuthenticationDTO.builder()
                                                .username(object.getTrainer().getUsername())
                                                .build())
                                .build())
                .name(object.getName())
                .type(TrainingTypeDTO.builder().name(object.getType().getName()).build())
                .date(object.getDate())
                .duration(object.getDuration())
                .build();
    }
}
