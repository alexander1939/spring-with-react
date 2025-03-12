package com.softhugordc.taskmanagerappv1.service;

import com.softhugordc.taskmanagerappv1.domain.TaskEntity;
import com.softhugordc.taskmanagerappv1.dto.response.TaskSummaryResponseDTO;
import com.softhugordc.taskmanagerappv1.exception.DatabaseOperationException;
import com.softhugordc.taskmanagerappv1.exception.TaskNotFoundException;
import com.softhugordc.taskmanagerappv1.repository.ITaskRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//Servicio para hacer operaciones con tareas
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements ITaskService {

    private final ITaskRepository taskRepository;

    //Buscar una tarea por su id
    @Override
    public Optional<TaskEntity> findById(String id) {
        Optional<TaskEntity> taskEntity = taskRepository.findById(id);
        if (taskEntity.isEmpty()) {
            throw new TaskNotFoundException("No existe una tarea con el id especificado", HttpStatus.NOT_FOUND);
        }
        return taskEntity;
    }

    //Comprobar si una tarea existe
    @Override
    public boolean existsById(String id) {
        return taskRepository.existsById(id);
    }

    //Buscar todas las tares por el id de un usuario
    @Override
    public List<TaskEntity> findByIdUser(String idUser) {
        List<TaskEntity> taskEntities = taskRepository.findByIdUser(idUser);
        if (taskEntities.isEmpty()) {
            throw new TaskNotFoundException("No existen tareas vinculadas al idUser especificado", HttpStatus.NOT_FOUND);
        }
        return taskEntities;
    }

    //Buscar todas las tareas, devuelve una lista con representaciones de TaskEntity
    @Override
    public List<TaskSummaryResponseDTO> findAllTasks() {
        List<TaskEntity> taskEntities = taskRepository.findAll();
        return taskEntities.stream()
                .map(task -> TaskSummaryResponseDTO.builder()
                        .id(task.getId())
                        .title(task.getTitle())
                        .description(task.getDescription())
                        .build())
                .collect(Collectors.toList());
    }

    //Crear una tarea, devuelve una TaskEntity
    @Override
    @Transactional
    public TaskEntity save(TaskEntity taskEntity) {
        try {
            return taskRepository.save(taskEntity);
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Error al intentar guardar la tarea");
        }
    }

    //Crear una tarea, devuelve una representacion de TaskEntity
    @Override
    @Transactional
    public TaskSummaryResponseDTO saveTask(TaskEntity taskEntity) {
        try {
            TaskEntity savedTaskEntity = taskRepository.save(taskEntity);
            return TaskSummaryResponseDTO.builder()
                    .id(savedTaskEntity.getId())
                    .title(savedTaskEntity.getTitle())
                    .description(savedTaskEntity.getDescription())
                    .build();
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Error al intentar guardar la tarea");
        }
    }

    //Eliminar una tarea por su id
    @Override
    @Transactional
    public void deleteTaskById(String id) {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException("No existe una tarea con el id especificado", HttpStatus.NOT_FOUND);
        }
        try {
            taskRepository.deleteById(id);
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Error al intentar eliminar la tarea con el id especificado");
        }
    }
}
