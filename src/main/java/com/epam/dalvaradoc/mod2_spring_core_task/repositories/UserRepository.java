/* (C)2025 */
package com.epam.dalvaradoc.mod2_spring_core_task.repositories;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);

    List<User> findByFirstNameAndLastName(String firstName, String lastName);
}
