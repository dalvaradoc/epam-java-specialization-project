package com.epam.dalvaradoc.mod2_spring_core_task.utils;

import java.util.Random;

import com.epam.dalvaradoc.mod2_spring_core_task.repositories.UserRepository;

public class UserUtils {
  private static final String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890*-()/#!$%&=?¿";
  private static final Random rnd = new Random();

  private UserUtils(){}

  public static String getSaltString() {
    StringBuilder salt = new StringBuilder();
    while (salt.length() < 10) { // length of the random string.
      int index = (int) (rnd.nextFloat() * SALTCHARS.length());
      salt.append(SALTCHARS.charAt(index));
    }
    return salt.toString();
  }

  public static String createUsername(String firstName, String lastName, UserRepository repository) {
    long repeated = repository.findByFirstNameAndLastName(firstName, lastName).size();
    return firstName + "." + lastName + (repeated > 0 ? "#" + (repeated+1) : "");
  }
}
