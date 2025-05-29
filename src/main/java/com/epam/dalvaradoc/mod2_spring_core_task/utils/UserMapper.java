/* (C)2025 */
package com.epam.dalvaradoc.mod2_spring_core_task.utils;

public interface UserMapper<C, D> extends Mapper<C, D> {
    D toDTOwithoutPassword(C object);
}
