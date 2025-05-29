/* (C)2025 */
package com.epam.dalvaradoc.mod2_spring_core_task.utils;

import com.epam.dalvaradoc.mod2_spring_core_task.repositories.UserRepository;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserUtils {
    private static final String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890*-()/#!$%&=?¿";
    private static final Random rnd = new Random();

    private UserRepository userRepository;

    @Autowired
    public UserUtils(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static String getSaltString() {
        StringBuilder salt = new StringBuilder();
        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        return salt.toString();
    }

    public String createUsername(String firstName, String lastName) {
        long repeated = userRepository.findByFirstNameAndLastName(firstName, lastName).size();
        return firstName + "." + lastName + (repeated > 0 ? "#" + (repeated + 1) : "");
    }
}
