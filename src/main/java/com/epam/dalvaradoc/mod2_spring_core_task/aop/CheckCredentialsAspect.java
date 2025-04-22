package com.epam.dalvaradoc.mod2_spring_core_task.aop;

import java.util.Optional;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.User;
import com.epam.dalvaradoc.mod2_spring_core_task.repositories.UserRepository;

@Aspect
@Component
public class CheckCredentialsAspect {
  private UserRepository userRepository;

  @Autowired
  public CheckCredentialsAspect(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Around("@annotation(CheckCredentials)")
  public Object checkCredentials(ProceedingJoinPoint joinPoint) throws Throwable {
    Object[] args = joinPoint.getArgs();
    if (!checkIfUsernameAndPasswordPassed(args) && !checkIfUserPassed(args)) {
        throw new IllegalArgumentException("Invalid method arguments for @CheckCredentials");
    }

    if (args.length == 1) {
      User user = (User) args[0];
      String username = user.getUsername();
      String password = user.getPassword();
      if (Optional.ofNullable(userRepository.findByUsername(username)).filter(u -> u.getPassword().equals(password)).isEmpty()) {
        throw new SecurityException("Invalid credentials");
      }
      return joinPoint.proceed(new Object[] {user});
    }

    String username = (String) args[args.length-2];
    String password = (String) args[args.length-1];

    User user = userRepository.findByUsername(username);
    if (Optional.ofNullable(user).filter(u -> u.getPassword().equals(password)).isEmpty()) {
      throw new SecurityException("Invalid credentials");
    }
    return joinPoint.proceed();
  }

  private boolean checkIfUsernameAndPasswordPassed(Object[] args) {
    return args.length > 1 && args[args.length-1] instanceof String && args[args.length-2] instanceof String;
  }

  private boolean checkIfUserPassed (Object[] args) {
    return args.length == 1 && User.class.isAssignableFrom(args[0].getClass());
  }
}
