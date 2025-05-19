package com.epam.dalvaradoc.mod2_spring_core_task.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.dalvaradoc.mod2_spring_core_task.dao.Trainee;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.AuthenticationDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TraineeDTO;
import com.epam.dalvaradoc.mod2_spring_core_task.dto.TraineeMapper;
import com.epam.dalvaradoc.mod2_spring_core_task.services.TraineeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/trainees")
public class TraineeController {

    private final TraineeService traineeService;
    private final TraineeMapper mapper = new TraineeMapper();

    @Autowired
    public TraineeController(TraineeService traineeService) {
        this.traineeService = traineeService;
    }

    @GetMapping
    public ResponseEntity<List<TraineeDTO>> getAllTrainees() {
        return ResponseEntity.ok(traineeService.getAllTrainees());
    }

    @PostMapping
    public ResponseEntity<AuthenticationDTO> registerTrainee(@Valid @RequestBody TraineeDTO dto) {
        TraineeDTO trainee = traineeService.createTrainee(
                dto.getFirstName(), dto.getLastName(), dto.getAddress(), dto.getBirthdate());
        return ResponseEntity.ok(trainee.getAuth());
    }
    
    @GetMapping("/{username}")
    public ResponseEntity<TraineeDTO> getTraineeByUsername(@PathVariable String username, @Valid @RequestBody AuthenticationDTO auth) {
        return ResponseEntity.ok(traineeService.getTraineeByUsername(auth));
    }

    @PutMapping
    public ResponseEntity<TraineeDTO> updateTrainee(@RequestBody TraineeDTO dto) {
        return ResponseEntity.ok(traineeService.updateTrainee(mapper.toObject(dto)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrainee(@PathVariable String id, @Valid @RequestBody AuthenticationDTO auth) {
        traineeService.deleteTraineeById(id, auth.getUsername(), auth.getPassword());
        return ResponseEntity.noContent().build();
    }
}