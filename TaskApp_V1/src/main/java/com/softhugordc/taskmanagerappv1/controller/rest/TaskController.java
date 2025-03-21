package com.softhugordc.taskmanagerappv1.controller.rest;

import com.softhugordc.taskmanagerappv1.domain.TaskEntity;
import com.softhugordc.taskmanagerappv1.dto.request.TaskRequestDTO;
import com.softhugordc.taskmanagerappv1.dto.response.TaskResponseDTO;
import com.softhugordc.taskmanagerappv1.dto.response.UserResponseDTO;
import com.softhugordc.taskmanagerappv1.service.ITaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

//Api para las tareas
@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Validated
public class TaskController {

    private final ITaskService taskService;

    //Obtener una tarea por su id
    @GetMapping("/get-by-id")
    public ResponseEntity<?> getById(@RequestParam(required = false) @NotBlank(message = "El campo id no debe estar vacio") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.findById(id));
    }

    //Obtener todas las tareas
    @GetMapping("/get-all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.findAll());
    }

    //Obtener todas las tareas de un usuario
    @GetMapping("/get-all-by-userid")
    public ResponseEntity<?> getAllByIdUser(@RequestParam(required = false) @NotBlank(message = "El campo id de usuario no debe estar vacio") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.findAllTasksByIdUser(id));
    }

    //Crear una tarea con el usuario
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestParam(required = false) @NotBlank(message = "El campo id de usuario no debe estar vacio") String id, @Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.save(taskRequestDTO, id));
    }

    //Eliminar una tarea
    @DeleteMapping("/delete-by-id")
    public ResponseEntity<?> delete(@RequestParam(required = false) @NotBlank(message = "El campo de id no debe estar vacio") String id) {
        taskService.deleteTaskById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
