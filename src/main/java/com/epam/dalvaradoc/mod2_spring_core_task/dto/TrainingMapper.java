package com.epam.dalvaradoc.mod2_spring_core_task.dto;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.Training;
import com.epam.dalvaradoc.mod2_spring_core_task.utils.Mapper;

public class TrainingMapper implements Mapper<Training, TrainingDTO> {
    @Override
    public Training toObject(TrainingDTO dto) {
      Training training = new Training();
      training.setTrainingId(dto.getTrainingId());
      training.setTrainee(dto.getTrainee());
      training.setTrainer(dto.getTrainer());
      training.setName(dto.getName());
      training.setType(dto.getType());
      training.setDate(dto.getDate());
      training.setDuration(dto.getDuration());
      return training;
    }

    @Override
    public TrainingDTO toDTO(Training object) {
      return TrainingDTO.builder()
          .trainingId(object.getTrainingId())
          .trainee(object.getTrainee())
          .trainer(object.getTrainer())
          .name(object.getName())
          .type(object.getType())
          .date(object.getDate())
          .duration(object.getDuration())
          .build();
    }
}
