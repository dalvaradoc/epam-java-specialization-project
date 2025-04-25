package com.epam.dalvaradoc.mod2_spring_core_task.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NameLikeStringValidator implements ConstraintValidator<NameLikeStringConstraint, String> {

  @Override
  public boolean isValid(String string, ConstraintValidatorContext context) {
    return string != null && string.matches("^[a-zA-Z ']+$") && (string.length() > 2 && string.length() < 31);
  }

}
