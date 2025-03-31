package com.epam.dalvaradoc.mod2_spring_core_task.configs;

import java.io.FileInputStream;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainee;
import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainer;
import com.epam.dalvaradoc.mod2_spring_core_task.dao.Training;
import com.epam.dalvaradoc.mod2_spring_core_task.dao.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class StorageBeansConfig {
  @Value("${my.properties.trainees-data-file}")
  private String traineesMapFile;

  @Value("${my.properties.trainers-data-file}")
  private String trainersMapFile;

  @Value("${my.properties.training-data-file}")
  private String trainingMapFile;

  @Bean
  public Map<String, Trainee> traineesMap(){
    return getData(traineesMapFile, Trainee.class, new TypeReference<List<Trainee>>() {});
  }

  @Bean
  public Map<String, Trainer> trainersMap() {
    return getData(trainersMapFile, Trainer.class, new TypeReference<List<Trainer>>() {});
  }

  @Bean
  public Map<String, Training> trainingMap() {
    return getData(trainingMapFile, Training.class, new TypeReference<List<Training>>() {});
  }

  public <V> Map<String,V> getData(String dataFileRoute, Class<V> valueClass, TypeReference<List<V>> typeReference) {
    Map<String, V> values = new TreeMap<>();
    try (FileInputStream input = new FileInputStream(dataFileRoute)) {
      ObjectMapper mapper = JsonMapper.builder().build();
      List<V> listOfObjects = mapper.readValue(input, typeReference);
      if (User.class.isAssignableFrom(valueClass)){
        values = listOfObjects.stream().collect(Collectors.toMap(o -> ((User)o).getUserId(), o -> o));
      } else if (Training.class.isAssignableFrom(valueClass)){
        values = listOfObjects.stream().collect(Collectors.toMap(o -> ((Training)o).getName(), o -> o));
      }
      LOGGER.info("Finished loading mock data for " + valueClass.getSimpleName() + " map");
      return values;
    } catch (Exception e) {
      LOGGER.error("Error loading data for " + valueClass.getSimpleName(), e);
    }
    return new TreeMap<>();
  }
}
