package com.epam.dalvaradoc.mod2_spring_core_task.configs;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainee;
import com.epam.dalvaradoc.mod2_spring_core_task.dao.User;
import com.fasterxml.jackson.core.type.TypeReference;
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
    Map<String, Trainee> result = getData(traineesMapFile, Trainee.class, new TypeReference<List<Trainee>>() {});
    // result.forEach((v,k) -> LOGGER.info(v + " " + k));
    return result;
  }

  public <V> Map<String,V> getData(String dataFileRoute, Class<V> valueClass, TypeReference<List<V>> typeReference) {
    Map<String, V> values = new TreeMap<>();
    try (FileInputStream input = new FileInputStream(dataFileRoute)) {
      ObjectMapper mapper = JsonMapper.builder().build();
      List<V> listOfObjects = mapper.readValue(input, typeReference);
      // LOGGER.info(listOfObjects.toString());
      if (User.class.isAssignableFrom(valueClass)){
        values = listOfObjects.stream().collect(Collectors.toMap(o -> ((User)o).getUserId(), o -> o));
        LOGGER.info(listOfObjects.get(0).getClass().getName());
      }
      LOGGER.info("Finished loading mock data for " + valueClass.getSimpleName() + " map");
      return values;
    } catch (Exception e) {
      e.printStackTrace();
      LOGGER.error("WTF?????");
      LOGGER.error(e.getMessage());
    }
    return new TreeMap<>();
  }
}
