package com.epam.dalvaradoc.mod2_spring_core_task.utils;

import java.util.Map;
import java.util.Random;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.User;

public class UserUtils {

  private UserUtils(){}

  public static String getSaltString() {
    String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890*-()/#!$%&=?¿";
    StringBuilder salt = new StringBuilder();
    Random rnd = new Random();
    while (salt.length() < 10) { // length of the random string.
      int index = (int) (rnd.nextFloat() * SALTCHARS.length());
      salt.append(SALTCHARS.charAt(index));
    }
    String saltStr = salt.toString();
    return saltStr;
  }

  public static <V extends User> String createUsername(String firstName, String lastName, Map<String,V> userMap){
    long repeated = userMap.values().stream().filter(o -> o.getFirstName().equals(firstName) && o.getLastName().equals(lastName)).count();
    return firstName + "." + lastName + (repeated > 0 ? "#" + (repeated+1) : "");
  }
}
