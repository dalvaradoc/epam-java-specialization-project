package com.epam.dalvaradoc.mod2_spring_core_task.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.epam.dalvaradoc.mod2_spring_core_task.dto.AuthenticationDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.services.UserService;
import com.epam.dalvaradoc.mod2_spring_core_task.utils.SwaggerExamples;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@Validated
@Tag(name = "Authentication Management", description = "Endpoints for user authentication and password management")
public class GeneralController {
  private UserService userService;

  @Autowired
  public GeneralController(UserService userService) {
    this.userService = userService;
  }

  @Operation(summary = "Login user", description = "Authenticates a user with their credentials", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Credentials", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerExamples.GENERAL_AUTH_REQBODY))))
  @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfullyauthenticated"),
      @ApiResponse(responseCode = "401", description = "Bad credentials")
  })
  @GetMapping("/login")
  public ResponseEntity<Void> login(@Valid @RequestBody AuthenticationDTO auth) {
    userService.login(auth);
    return ResponseEntity.ok().build();
  }

  @Operation(summary = "Change password", description = "Changes the password for an authenticated user", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Current credentials and new password", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerExamples.GENERAL_CHANGE_PASSWORD_REQBODY))))
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Password successfully changed", content = @Content(mediaType = "application/json", examples = @ExampleObject(value = SwaggerExamples.GENERAL_CHANGE_PASSWORD_RESBODY))),
      @ApiResponse(responseCode = "401", description = "Bad credentials")
  })
  @PutMapping("/change-password")
  public ResponseEntity<AuthenticationDTO> changePassword(@RequestBody Map<String, String> requestBody) {
    LOGGER.info("Trying password change for user: " + requestBody.get("username"));
    AuthenticationDTO auth = new AuthenticationDTO(requestBody.get("username"), requestBody.get("password"));
    return ResponseEntity.ok(userService.changePassword(requestBody.get("newPassword"), auth));
  }
}
