package com.epam.dalvaradoc.mod2_spring_core_task.validations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = NameLikeStringValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface NameLikeStringConstraint {
  String message() default "Name must only contain letters, spaces and apostrophes. It must be between 3 and 30 characters.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
