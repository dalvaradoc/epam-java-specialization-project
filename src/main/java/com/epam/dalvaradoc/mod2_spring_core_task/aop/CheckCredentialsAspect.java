package com.epam.dalvaradoc.mod2_spring_core_task.aop;

import java.util.Optional;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.User;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.AuthenticationDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.errors.BadCredentialsException;
import com.epam.dalvaradoc.mod2_spring_core_task.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class CheckCredentialsAspect {
  private UserRepository userRepository;

  @Autowired
  public CheckCredentialsAspect(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Around("@annotation(CheckCredentials)")
  public Object checkCredentials(ProceedingJoinPoint joinPoint) throws Throwable {
    Object[] args = joinPoint.getArgs();
    if (args.length == 0 || (!checkIfUsernameAndPasswordPassed(args) && !checkIfUserPassed(args)
        && !checkIfAuthenticationDTOPassed(args))) {
      LOGGER.error("Invalid method arguments for @CheckCredentials");
      throw new IllegalArgumentException("Invalid method arguments for @CheckCredentials");
    }

    String username;
    String password;

    if (checkIfUserPassed(args)) {
      User user = (User) args[args.length - 1];
      username = user.getUsername();
      password = user.getPassword();
    } else if (checkIfAuthenticationDTOPassed(args)) {
      AuthenticationDTO authenticationDTO = (AuthenticationDTO) args[args.length - 1];
      username = authenticationDTO.getUsername();
      password = authenticationDTO.getPassword();
    } else {
      username = (String) args[args.length - 2];
      password = (String) args[args.length - 1];
    }

    User user = userRepository.findByUsername(username);
    if (user == null) {
      LOGGER.error("User not found: " + username);
      throw new BadCredentialsException("Invalid credentials");
    }

    if (Optional.ofNullable(user).filter(u -> u.getPassword().equals(password)).isEmpty()) {
      LOGGER.error("Invalid credentials for user: " + username);
      throw new BadCredentialsException("Invalid credentials");
    }
    return joinPoint.proceed();
  }

  private boolean checkIfUsernameAndPasswordPassed(Object[] args) {
    return args.length > 1 && args[args.length - 1] instanceof String && args[args.length - 2] instanceof String;
  }

  private boolean checkIfUserPassed(Object[] args) {
    return args[args.length - 1] != null &&  User.class.isAssignableFrom(args[args.length - 1].getClass());
  }

  private boolean checkIfAuthenticationDTOPassed(Object[] args) {
    return  args[args.length - 1] != null && AuthenticationDTO.class.isAssignableFrom(args[args.length - 1].getClass());
  }
}
