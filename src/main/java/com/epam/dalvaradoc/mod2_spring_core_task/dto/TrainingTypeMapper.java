/* (C)2025 */
package com.epam.dalvaradoc.mod2_spring_core_task.dto;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.TrainingType;
import com.epam.dalvaradoc.mod2_spring_core_task.utils.Mapper;

public class TrainingTypeMapper implements Mapper<TrainingType, TrainingTypeDTO> {

    @Override
    public TrainingType toObject(TrainingTypeDTO dto) {
        return new TrainingType(dto.getId(), dto.getName());
    }

    @Override
    public TrainingTypeDTO toDTO(TrainingType object) {
        return TrainingTypeDTO.builder().name(object.getName()).build();
    }
}
