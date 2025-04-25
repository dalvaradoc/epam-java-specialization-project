package com.epam.dalvaradoc.mod2_spring_core_task.validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<UsernameConstraint, String> {

  @Override
  public boolean isValid(String username, ConstraintValidatorContext context) {
    return username != null && username.matches("^[a-zA-Z0-9 ,']+\\.[a-zA-Z0-9 ,']+(#[1-9]+)?$");
  }
}
