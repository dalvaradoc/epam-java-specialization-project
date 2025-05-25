package com.epam.dalvaradoc.mod2_spring_core_task.utils;

public class SwaggerExamples {
  private SwaggerExamples() {
  }

  public static final String GENERAL_AUTH_REQBODY = """

      """;

  public static final String GENERAL_CHANGE_PASSWORD_REQBODY = """
      {
          "username": "john.doe",
          "password": "currentPassword",
          "newPassword": "newPassword123"
      }
      """;

  public static final String GENERAL_CHANGE_PASSWORD_RESBODY = """
          {
              "username": "john.doe",
              "password": "newPassword123"
          }
      """;
}
