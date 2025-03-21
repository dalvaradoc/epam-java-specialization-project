package com.epam.dalvaradoc.mod2_spring_core_task.configs;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class StorageBeansConfig {
  @Value("${my.properties.trainees-map-file}")
  private String TRAINEES_MAP_FILE;

  @Bean
  public Map<String, Trainee> getTrainees(){
    Map<String, Trainee> result = getData(TRAINEES_MAP_FILE, Trainee.class);
    result.forEach((v,k) -> LOGGER.info(v + " " + k));
    return result;
  }

  public <V> Map<String,V> getData(String dataFileRoute, Class<V> valueClass) {
    Map<String, V> values = new HashMap<>();
    Properties valuesData = new Properties();
    try (FileInputStream input = new FileInputStream(dataFileRoute)) {
      valuesData.load(input);      
      valuesData.forEach((key, value) -> {
        String userId = (String) key;
        try {
          V valueObject = new ObjectMapper().readValue((String) value, valueClass);
          values.put(userId, valueObject);
        } catch (JsonProcessingException e) {
          e.printStackTrace();
        }
      });
      return values;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new HashMap<>();
  }
}
