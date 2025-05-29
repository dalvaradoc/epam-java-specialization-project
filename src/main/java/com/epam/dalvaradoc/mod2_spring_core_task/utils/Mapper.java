/* (C)2025 */
package com.epam.dalvaradoc.mod2_spring_core_task.utils;

public interface Mapper<C, D> {
    C toObject(D dto);

    D toDTO(C object);
}
