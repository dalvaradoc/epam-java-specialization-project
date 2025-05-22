package com.epam.dalvaradoc.mod2_spring_core_task.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.epam.dalvaradoc.mod2_spring_core_task.aop.CheckCredentials;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.AuthenticationDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.services.UserService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class GeneralController {
  private UserService userService;

  @Autowired
  public GeneralController(UserService userService) {
    this.userService = userService;
  }

  @CheckCredentials
  @GetMapping("/login")
  public ResponseEntity<String> login(@Valid @RequestBody AuthenticationDTO auth) {
    return ResponseEntity.ok("Login successful for user: " + auth.getUsername());
  }

  @PutMapping("/change-password")
  public ResponseEntity<AuthenticationDTO> changePassword(@RequestBody Map<String, String> requestBody) {
    LOGGER.info("Password changed successfully for user: " + requestBody.get("username"));
    AuthenticationDTO auth = new AuthenticationDTO(requestBody.get("username"), requestBody.get("password"));
    return ResponseEntity.ok(userService.changePassword(requestBody.get("newPassword"), auth));
  }
}
