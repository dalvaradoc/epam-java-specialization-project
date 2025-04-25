package com.epam.dalvaradoc.mod2_spring_core_task.validations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = UsernameValidator.class)
@Target( { ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameConstraint {
  String message() default "Username must follow the template firstName.lastName";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
