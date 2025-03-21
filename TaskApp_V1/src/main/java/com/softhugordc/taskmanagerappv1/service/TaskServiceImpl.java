package com.softhugordc.taskmanagerappv1.service;

import com.softhugordc.taskmanagerappv1.domain.TaskEntity;
import com.softhugordc.taskmanagerappv1.domain.UserEntity;
import com.softhugordc.taskmanagerappv1.dto.request.TaskRequestDTO;
import com.softhugordc.taskmanagerappv1.dto.response.TaskResponseDTO;
import com.softhugordc.taskmanagerappv1.dto.response.UserResponseDTO;
import com.softhugordc.taskmanagerappv1.exception.DatabaseOperationException;
import com.softhugordc.taskmanagerappv1.exception.TaskNotFoundException;
import com.softhugordc.taskmanagerappv1.exception.UserNotFoundException;
import com.softhugordc.taskmanagerappv1.repository.ITaskRepository;
import com.softhugordc.taskmanagerappv1.repository.IUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//Servicio para hacer operaciones con tareas
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements ITaskService {

    private final ITaskRepository taskRepository;
    private final IUserRepository userRepository;

    @Override
    public Optional<TaskEntity> findById(String id) {
        Optional<TaskEntity> taskEntity = taskRepository.findById(id);
        if (taskEntity.isEmpty()) {
            throw new TaskNotFoundException(String.format("El id %s no corresponde a ninguna tarea", id), HttpStatus.NOT_FOUND);
        }
        return taskEntity;
    }

    //Buscar todas las tareas por el id de usuario
    @Override
    public List<TaskEntity> findAllByIdUser(String idUser) {
        List<TaskEntity> taskEntities = taskRepository.findAllByIdUser(idUser);
        if (taskEntities.isEmpty()) {
            throw new TaskNotFoundException(String.format("El id %s no tiene tareas vinculadas", idUser), HttpStatus.NOT_FOUND);
        }
        return taskEntities;
    }

    //Buscar todas las tareas por el id de usuario, devuelve TaskResponseDTO
    @Override
    public List<TaskResponseDTO> findAllTasksByIdUser(String idUser) {
        return taskRepository.findAllByIdUser(idUser).stream()
                .map(taskEntity -> TaskResponseDTO.builder()
                        .id(taskEntity.getId())
                        .title(taskEntity.getTitle())
                        .description(taskEntity.getDescription())
                        .user(UserResponseDTO.builder()
                                .id(taskEntity.getUser().getId())
                                .name(taskEntity.getUser().getName())
                                .lastName(taskEntity.getUser().getLastName())
                                .email(taskEntity.getUser().getEmail())
                                .build())
                        .build())
                .toList();
    }

    //Buscar todas las tareas
    @Override
    public List<TaskEntity> findAll() {
        return taskRepository.findAll();
    }

    //Guardar una tarea
    @Override
    @Transactional
    public TaskEntity save(TaskEntity taskEntity, String idUser) {
        UserEntity userEntity = userRepository.findById(idUser)
                .orElseThrow(() -> new UserNotFoundException(String.format("El id %s no corresponde a ningun usuario", idUser), HttpStatus.NOT_FOUND));
        taskEntity.setUser(userEntity);
        try {
            return taskRepository.save(taskEntity);
        } catch (DataAccessException e) {
            throw new DatabaseOperationException(String.format("Error al intentar guardar la tarea del usuario con id %s", idUser));
        }
    }

    //Guardar una tarea, pero con otra respuesta
    @Override
    public TaskResponseDTO save(TaskRequestDTO taskRequestDTO, String idUser) {
        UserEntity userEntity = userRepository.findById(idUser)
                .orElseThrow(() -> new UserNotFoundException(String.format("El id %s no corresponde a ningun usuario", idUser), HttpStatus.NOT_FOUND));
        TaskEntity taskEntity = TaskEntity.builder()
                .title(taskRequestDTO.getTitle())
                .description(taskRequestDTO.getDescription())
                .user(userEntity)
                .build();
        try {
            TaskEntity savedTaskEntity = taskRepository.save(taskEntity);
            UserResponseDTO userResponseDTO = UserResponseDTO.builder()
                    .id(savedTaskEntity.getUser().getId())
                    .name(savedTaskEntity.getUser().getName())
                    .lastName(savedTaskEntity.getUser().getLastName())
                    .email(savedTaskEntity.getUser().getEmail())
                    .build();
            return TaskResponseDTO.builder()
                    .id(savedTaskEntity.getId())
                    .title(savedTaskEntity.getTitle())
                    .description(savedTaskEntity.getDescription())
                    .user(userResponseDTO)
                    .build();
        } catch (DataAccessException e) {
            throw new DatabaseOperationException(String.format("Error al intentar guardar la tarea del usuario con id %s", idUser));
        }
    }

    //Eliminar una tarea por su id
    @Override
    @Transactional
    public void deleteTaskById(String id) {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException(String.format("El id %s no corresponde a ninguna tarea", id), HttpStatus.NOT_FOUND);
        }
        try {
            taskRepository.deleteById(id);
        } catch (DataAccessException e) {
            throw new DatabaseOperationException(String.format("Error al intentar eliminar la tarea con el id %s", id));
        }
    }

}
