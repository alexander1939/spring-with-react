package com.softhugordc.taskmanagerappv1.controller.rest;

import com.softhugordc.taskmanagerappv1.domain.TaskEntity;
import com.softhugordc.taskmanagerappv1.dto.request.TaskRequestDTO;
import com.softhugordc.taskmanagerappv1.dto.response.TaskSummaryResponseDTO;
import com.softhugordc.taskmanagerappv1.service.ITaskService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//Api para las tareas
@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Validated
public class TaskController {

    private final ITaskService taskService;

    //Obtener una tarea por su id
    @GetMapping("/get-by-id")
    public ResponseEntity<?> getById(@RequestParam(required = false) @NotBlank(message = "El id no puede estar vacio") String id) {
        Optional<TaskEntity> taskEntity = taskService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(taskEntity);
    }

    //Obtener todas las tareas
    @GetMapping("/get-all-tasks")
    public ResponseEntity<?> getAll(){
        List<TaskSummaryResponseDTO> taskSummaryResponseDTOS = taskService.findAllTasks();
        return ResponseEntity.status(HttpStatus.OK).body(taskSummaryResponseDTOS);
    }

    //Crear una tarea
    @PostMapping("/create")
    public ResponseEntity<?> create(@Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        TaskEntity taskEntity = TaskEntity.builder()
                .title(taskRequestDTO.getTitle())
                .description(taskRequestDTO.getDescription())
                .build();
        TaskSummaryResponseDTO taskSummaryResponseDTO = taskService.saveTask(taskEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskSummaryResponseDTO);
    }

    //Eliminar una tarea
    @DeleteMapping("/delete-by-id")
    public ResponseEntity<?> delete(@RequestParam(required = false) @NotBlank(message = "El id no puede estar vacio") String id) {
        taskService.deleteTaskById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
