package com.epam.dalvaradoc.mod2_spring_core_task.configs;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class StorageBeansConfig {
  @Value("${my.properties.trainees-map-file}")
  private String traineesMapFile;

  @Bean
  public Map<String, Trainee> traineesMap(){
    Map<String, Trainee> result = getData(traineesMapFile, Trainee.class);
    // result.forEach((v,k) -> LOGGER.info(v + " " + k));
    return result;
  }

  public <V> Map<String,V> getData(String dataFileRoute, Class<V> valueClass) {
    Map<String, V> values = new TreeMap<>();
    Properties valuesData = new Properties();
    try (FileInputStream input = new FileInputStream(dataFileRoute)) {
      ObjectMapper mapper = JsonMapper.builder().enable(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER).build();
      valuesData.load(input);      
      valuesData.forEach((key, value) -> {
        String userId = (String) key;
        try {
          V valueObject = mapper.readValue((String) value, valueClass);
          // LOGGER.info(((V) valueObject).toString());
          values.put(userId, valueObject);
        } catch (JsonProcessingException e) {
          LOGGER.error(e.getMessage());
        }
      });
      LOGGER.info("Finished loading mock data for " + valueClass.getSimpleName() + " map");
      return values;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return new HashMap<>();
  }
}
