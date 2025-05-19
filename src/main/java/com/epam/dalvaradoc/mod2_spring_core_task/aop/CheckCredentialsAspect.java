package com.epam.dalvaradoc.mod2_spring_core_task.aop;

import java.util.Optional;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    if (args.length == 0 && !checkIfUsernameAndPasswordPassed(args) && !checkIfUserPassed(args) && !checkIfAuthenticationDTOPassed(args)) {
        LOGGER.error("Invalid method arguments for @CheckCredentials");
        throw new IllegalArgumentException("Invalid method arguments for @CheckCredentials");
    }

    if (checkIfUserPassed(args)) {
      User user = (User) args[args.length-1];
      String username = user.getUsername();
      String password = user.getPassword();
      if (Optional.ofNullable(userRepository.findByUsername(username)).filter(u -> u.getPassword().equals(password)).isEmpty()) {
        LOGGER.error("Invalid credentials for user: " + username);
        throw new BadCredentialsException("Invalid credentials");
      }
      return joinPoint.proceed();
    }

    if (checkIfAuthenticationDTOPassed(args)){
      AuthenticationDTO authenticationDTO = (AuthenticationDTO) args[args.length-1];
      String username = authenticationDTO.getUsername();
      String password = authenticationDTO.getPassword();
      if (Optional.ofNullable(userRepository.findByUsername(username)).filter(u -> u.getPassword().equals(password)).isEmpty()) {
        LOGGER.error("Invalid credentials for user: " + username);
        throw new BadCredentialsException("Invalid credentials");
      }
      return joinPoint.proceed();
    }

    String username = (String) args[args.length-2];
    String password = (String) args[args.length-1];

    User user = userRepository.findByUsername(username);
    if (Optional.ofNullable(user).filter(u -> u.getPassword().equals(password)).isEmpty()) {
      LOGGER.error("Invalid credentials for user: " + username);
      throw new BadCredentialsException("Invalid credentials");
    }
    return joinPoint.proceed();
  }

  private boolean checkIfUsernameAndPasswordPassed(Object[] args) {
    return args.length > 1 && args[args.length-1] instanceof String && args[args.length-2] instanceof String;
  }

  private boolean checkIfUserPassed (Object[] args) {
    return User.class.isAssignableFrom(args[args.length-1].getClass());
  }

  private boolean checkIfAuthenticationDTOPassed (Object[] args) {
    return AuthenticationDTO.class.isAssignableFrom(args[args.length-1].getClass());
  }
}
