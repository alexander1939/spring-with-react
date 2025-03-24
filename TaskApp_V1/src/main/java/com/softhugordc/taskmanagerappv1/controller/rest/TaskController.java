package com.softhugordc.taskmanagerappv1.controller.rest;

import com.softhugordc.taskmanagerappv1.dto.request.TaskSaveRequestDTO;
import com.softhugordc.taskmanagerappv1.dto.request.TaskPatchRequestDTO;
import com.softhugordc.taskmanagerappv1.dto.request.TaskUpdateRequestDTO;
import com.softhugordc.taskmanagerappv1.service.ITaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

//Controlador para las tareas
@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Validated
public class TaskController {

    private final ITaskService taskService;

    //Obtener una tarea por su id
    @GetMapping("/get-by-id")
    public ResponseEntity<?> getById(@RequestParam(required = false) @NotBlank(message = "El campo id no debe estar vacio") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.findByIdTask(id));
    }

    //Obtener todas las tareas
    @GetMapping("/get-all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.findAllTasks());
    }

    //Obtener todas las tareas de un usuario
    @GetMapping("/get-all-by-id-user")
    public ResponseEntity<?> getAllByIdUser(@RequestParam(required = false) @NotBlank(message = "El campo id de usuario no debe estar vacio") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.findAllTasksByIdUser(id));
    }

    //Crear una tarea
    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody TaskSaveRequestDTO taskSaveRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.saveTask(taskSaveRequestDTO));
    }

    //Actualizar una tarea
    @PutMapping("/update")
    public ResponseEntity<?> update(@Valid @RequestBody TaskUpdateRequestDTO taskUpdateRequestDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.updateTask(taskUpdateRequestDTO));
    }

    @PatchMapping("/patch")
    public ResponseEntity<?> patch(@Valid @RequestBody TaskPatchRequestDTO taskPatchRequestDTO){
        return ResponseEntity.status(HttpStatus.OK).body(taskService.patchTask(taskPatchRequestDTO));
    }

    //Eliminar una tarea
    @DeleteMapping("/delete-by-id")
    public ResponseEntity<?> delete(@RequestParam(required = false) @NotBlank(message = "El campo id no debe estar vacio") String id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(taskService.deleteTaskById(id));
    }

}
