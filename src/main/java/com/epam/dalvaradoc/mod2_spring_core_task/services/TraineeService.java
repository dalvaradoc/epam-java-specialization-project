/* (C)2025 */
package com.epam.dalvaradoc.mod2_spring_core_task.services;

import com.epam.dalvaradoc.mod2_spring_core_task.aop.CheckCredentials;
import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainee;
import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainer;
import com.epam.dalvaradoc.mod2_spring_core_task.dao.Training;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.AuthenticationDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TraineeDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TraineeMapper;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainerDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainerMapper;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainingDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TrainingMapper;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.UpdateTraineeDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.repositories.TraineeRepository;
import com.epam.dalvaradoc.mod2_spring_core_task.repositories.TrainerRepository;
import com.epam.dalvaradoc.mod2_spring_core_task.utils.UserUtils;
import com.epam.dalvaradoc.mod2_spring_core_task.validations.NameLikeStringConstraint;
import com.epam.dalvaradoc.mod2_spring_core_task.validations.UsernameConstraint;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import java.sql.Date;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Slf4j
@Validated
public class TraineeService {
    private TraineeRepository traineeRepository;
    private TrainerRepository trainerRepository;

    private UserUtils userUtils;
    private final TraineeMapper mapper = new TraineeMapper();
    private final TrainerMapper trainerMapper = new TrainerMapper();
    private final TrainingMapper trainingMapper = new TrainingMapper();

    @Autowired
    public TraineeService(
            TraineeRepository traineeRepository,
            TrainerRepository trainerRepository,
            UserUtils userUtils) {
        this.traineeRepository = traineeRepository;
        this.trainerRepository = trainerRepository;
        this.userUtils = userUtils;
    }

    public List<TraineeDTO> getAllTrainees() {
        return traineeRepository.findAll().stream().map(mapper::toDTO).toList();
    }

    @CheckCredentials
    public TraineeDTO getTraineeByUsername(@Valid AuthenticationDTO auth) {
        return Optional.ofNullable(auth.getUsername())
                .map(traineeRepository::findByUsername)
                .map(mapper::toDTO)
                .orElse(null);
    }

    public AuthenticationDTO createTrainee(
            @NameLikeStringConstraint String firstName,
            @NameLikeStringConstraint String lastName,
            @NotNull String address,
            @Past Date birthdate) {
        String username = userUtils.createUsername(firstName, lastName);
        String password = UserUtils.getSaltString();

        Trainee trainee = new Trainee();
        trainee.setFirstName(firstName);
        trainee.setLastName(lastName);
        trainee.setUsername(username);
        trainee.setPassword(password);
        trainee.setActive(true);
        trainee.setAddress(address);
        trainee.setBirthdate(birthdate);

        traineeRepository.save(trainee);
        return new AuthenticationDTO(username, password);
    }

    @CheckCredentials
    public TraineeDTO updateTrainee(
            @Valid UpdateTraineeDTO newTrainee, @Valid AuthenticationDTO auth) {
        Optional<Trainee> traineeOptional =
                Optional.ofNullable(
                        traineeRepository.findByUsername(newTrainee.getAuth().getUsername()));

        if (traineeOptional.isEmpty()) {
            return null;
        }

        Trainee trainee = traineeOptional.get();
        trainee.setFirstName(newTrainee.getFirstName());
        trainee.setLastName(newTrainee.getLastName());
        if (newTrainee.getBirthdate() != null) {
            trainee.setBirthdate(newTrainee.getBirthdate());
        }
        if (newTrainee.getAddress() != null) {
            trainee.setAddress(newTrainee.getAddress());
        }

        traineeRepository.save(trainee);
        LOGGER.info("Trainee updated: " + trainee.toString());
        TraineeDTO traineeDTO = mapper.toDTO(trainee);
        traineeDTO.setAuth(AuthenticationDTO.builder().username(trainee.getUsername()).build());
        return traineeDTO;
    }

    @CheckCredentials
    public boolean changePassword(
            @NotNull String newPassword,
            @UsernameConstraint String username,
            @NotNull String password) {
        Trainee trainee = traineeRepository.findByUsername(username);
        if (trainee == null) {
            return false;
        }

        trainee.setPassword(newPassword);
        traineeRepository.save(trainee);
        LOGGER.info("Trainee password changed: " + trainee.toString());
        return true;
    }

    @CheckCredentials
    public boolean changeActiveState(boolean active, @Valid AuthenticationDTO auth) {
        Optional<Trainee> traineeOptional =
                Optional.ofNullable(traineeRepository.findByUsername(auth.getUsername()));
        if (traineeOptional.isEmpty()) {
            return false;
        }
        Trainee trainee = traineeOptional.get();
        trainee.setActive(active);
        traineeRepository.save(trainee);
        LOGGER.info("Trainer active state changed: " + trainee.getUsername() + trainee.isActive());
        return trainee.isActive();
    }

    @CheckCredentials
    public void deleteTraineeById(
            @NotNull String userId, @UsernameConstraint String username, @NotNull String password) {
        traineeRepository.deleteById(userId);
        LOGGER.info("Trainee deleted: " + userId);
    }

    @CheckCredentials
    @Transactional
    public void deleteTraineeByUsername(
            @UsernameConstraint String username, @NotNull String password) {
        traineeRepository.deleteByUsername(username);
        LOGGER.info("Trainee deleted: " + username);
    }

    @CheckCredentials
    public List<TrainerDTO> getTrainersNotAssignedToTrainee(@Valid AuthenticationDTO auth) {
        return Optional.ofNullable(traineeRepository.findByUsername(auth.getUsername()))
                .map(
                        trainee ->
                                Optional.ofNullable(trainee.getTrainers())
                                        .orElse(Collections.emptySet()))
                .map(this::getTrainersNotAssignedToTrainee)
                .orElse(null);
    }

    private List<TrainerDTO> getTrainersNotAssignedToTrainee(Set<Trainer> traineesTrainers) {
        return trainerRepository.findAll().stream()
                .filter(trainer -> trainer.isActive() && !traineesTrainers.contains(trainer))
                .map(trainerMapper::toDTO)
                .toList();
    }

    @CheckCredentials
    public List<TrainerDTO> updateTrainersList(
            List<String> trainersUsernames, @Valid AuthenticationDTO auth) {
        Trainee trainee = traineeRepository.findByUsername(auth.getUsername());

        addTrainersToTrainee(trainersUsernames, trainee);

        traineeRepository.save(trainee);
        LOGGER.info("Trainee trainers list updated: " + trainee.getTrainers().toString());

        return trainee.getTrainers().stream().map(trainerMapper::toDTO).toList();
    }

    private void addTrainersToTrainee(List<String> trainersUsernames, Trainee trainee) {
        if (trainee.getTrainers() == null) {
            trainee.setTrainers(new HashSet<>());
        }

        Optional.ofNullable(trainersUsernames).orElse(List.of()).stream()
                .map(trainerRepository::findByUsername)
                .filter(Objects::nonNull)
                .forEach(trainer -> trainee.getTrainers().add(trainer));
    }

    @CheckCredentials
    public List<TrainingDTO> getTrainings(
            Map<String, Object> filters, @Valid AuthenticationDTO auth) {
        Trainee trainee = traineeRepository.findByUsername(auth.getUsername());
        if (trainee == null) {
            return List.of();
        }

        return trainee.getTrainings().stream()
                .filter(training -> getTrainingFiltersPredicate(filters, training))
                .map(trainingMapper::toDTO)
                .map(
                        dto -> {
                            dto.setTrainee(null);
                            dto.setTrainer(
                                    TrainerDTO.builder()
                                            .firstName(dto.getTrainer().getFirstName())
                                            .lastName(dto.getTrainer().getLastName())
                                            .build());
                            return dto;
                        })
                .toList();
    }

    private boolean getTrainingFiltersPredicate(Map<String, Object> filters, Training training) {
        if (filters.get("from") != null) {
            Date from = (Date) filters.get("from");
            if (training.getDate().before(from)) {
                return false;
            }
        }
        if (filters.get("to") != null) {
            Date to = (Date) filters.get("to");
            if (training.getDate().after(to)) {
                return false;
            }
        }
        if (filters.get("trainerName") != null) {
            String trainerName = (String) filters.get("trainerName");
            if (!training.getTrainer().getFirstName().equals(trainerName)
                    && !training.getTrainer().getLastName().equals(trainerName)) {
                return false;
            }
        }
        if (filters.get("trainingType") != null) {
            String trainingType = (String) filters.get("trainingType");
            if (!training.getType().getName().equals(trainingType)) {
                return false;
            }
        }
        return true;
    }
}
