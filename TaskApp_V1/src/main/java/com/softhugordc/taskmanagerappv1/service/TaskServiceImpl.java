package com.softhugordc.taskmanagerappv1.service;

import com.softhugordc.taskmanagerappv1.domain.TaskEntity;
import com.softhugordc.taskmanagerappv1.domain.UserEntity;
import com.softhugordc.taskmanagerappv1.dto.request.TaskSaveRequestDTO;
import com.softhugordc.taskmanagerappv1.dto.request.TaskPatchRequestDTO;
import com.softhugordc.taskmanagerappv1.dto.request.TaskUpdateRequestDTO;
import com.softhugordc.taskmanagerappv1.dto.response.MessageResponseDTO;
import com.softhugordc.taskmanagerappv1.dto.response.TaskResponseDTO;
import com.softhugordc.taskmanagerappv1.dto.response.UserResponseDTO;
import com.softhugordc.taskmanagerappv1.dto.response.TaskSummaryResponseDTO;
import com.softhugordc.taskmanagerappv1.exception.DatabaseOperationException;
import com.softhugordc.taskmanagerappv1.exception.TaskNotChangesException;
import com.softhugordc.taskmanagerappv1.exception.TaskNotFoundException;
import com.softhugordc.taskmanagerappv1.exception.UserNotFoundException;
import com.softhugordc.taskmanagerappv1.repository.ITaskRepository;
import com.softhugordc.taskmanagerappv1.repository.IUserRepository;
import com.softhugordc.taskmanagerappv1.security.service.AuthService;
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
    private final AuthService authService;

    //Buscar una tarea por su id, devuelve un Optional de TaskEntity
    @Override
    public Optional<TaskEntity> findById(String id) {
        return Optional.ofNullable(taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(String.format("El id %s no corresponde a ninguna tarea", id), HttpStatus.BAD_REQUEST)));
    }

    //Buscar una tarea por su id, devuelve un TaskResponseDTO
    @Override
    public TaskResponseDTO findByIdTask(String id) {
        TaskEntity taskEntityResult = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(String.format("El id %s no corresponde a ninguna tarea", id), HttpStatus.BAD_REQUEST));
        return TaskResponseDTO.builder()
                .id(taskEntityResult.getId())
                .title(taskEntityResult.getTitle())
                .description(taskEntityResult.getDescription())
                .user(UserResponseDTO.builder()
                        .id(taskEntityResult.getUser().getId())
                        .name(taskEntityResult.getUser().getName())
                        .lastName(taskEntityResult.getUser().getLastName())
                        .email(taskEntityResult.getUser().getEmail())
                        .build())
                .build();
    }

    //Buscar todas las tareas por el id de usuario, devuelve un List de TaskEntity
    @Override
    public List<TaskEntity> findAllByIdUser(String idUser) {
        if (!userRepository.existsById(idUser)) {
            throw new UserNotFoundException(String.format("El id %s no corresponde a ningun usuario", idUser), HttpStatus.BAD_REQUEST);
        }
        if(!authService.currentUser().getId().equals(idUser)) {
            throw new UserNotFoundException("No estas autorizado para acceder a los recursos de otro usuario", HttpStatus.BAD_REQUEST);
        }
        List<TaskEntity> taskEntitiesResult = taskRepository.findAllByIdUserWithUser(idUser);
        if (taskEntitiesResult.isEmpty()) {
            throw new TaskNotFoundException(String.format("El usuario con id %s no tiene tareas vinculadas", idUser), HttpStatus.BAD_REQUEST);
        }
        return taskEntitiesResult;
    }

    //Buscar todas las tareas por el id de usuario, devuelve un List de TaskSummaryResponseDTO
    @Override
    public List<TaskSummaryResponseDTO> findAllTasksByIdUser(String idUser) {
        if (!userRepository.existsById(idUser)) {
            throw new UserNotFoundException(String.format("El id %s no corresponde a ningun usuario", idUser), HttpStatus.BAD_REQUEST);
        }
        if(!authService.currentUser().getId().equals(idUser)) {
            throw new UserNotFoundException("No estas autorizado para acceder a los recursos de otro usuario", HttpStatus.BAD_REQUEST);
        }
        List<TaskEntity> taskEntitiesResult = taskRepository.findAllByIdUserWithUser(idUser);
        if (taskEntitiesResult.isEmpty()) {
            throw new TaskNotFoundException(String.format("El usuario con id %s no tiene tareas vinculadas", idUser), HttpStatus.BAD_REQUEST);
        }
        return taskEntitiesResult.stream()
                .map(taskEntityResult -> TaskSummaryResponseDTO.builder()
                        .id(taskEntityResult.getId())
                        .title(taskEntityResult.getTitle())
                        .description(taskEntityResult.getDescription())
                        .build())
                .toList();
    }

    //Buscar todas las tareas, devuelve un List de TaskEntity
    @Override
    public List<TaskEntity> findAll() {
        List<TaskEntity> taskEntitiesResult = taskRepository.findAll();
        if (taskEntitiesResult.isEmpty()) {
            throw new TaskNotFoundException("No hay tareas registradas", HttpStatus.BAD_REQUEST);
        }
        return taskEntitiesResult;
    }

    //Buscar todas las tareas, devuelve un List de TaskResponseDTO
    @Override
    public List<TaskResponseDTO> findAllTasks() {
        List<TaskEntity> taskEntitiesResult = taskRepository.findAll();
        if (taskEntitiesResult.isEmpty()) {
            throw new TaskNotFoundException("No hay tareas registradas", HttpStatus.BAD_REQUEST);
        }
        return taskEntitiesResult.stream()
                .map(taskEntityResult -> TaskResponseDTO.builder()
                        .id(taskEntityResult.getId())
                        .title(taskEntityResult.getTitle())
                        .description(taskEntityResult.getDescription())
                        .user(UserResponseDTO.builder()
                                .id(taskEntityResult.getUser().getId())
                                .name(taskEntityResult.getUser().getName())
                                .lastName(taskEntityResult.getUser().getLastName())
                                .email(taskEntityResult.getUser().getEmail())
                                .build())
                        .build())
                .toList();
    }

    //Guardar una tarea, devuelve un TaskEntity
    @Override
    @Transactional
    public TaskEntity save(TaskEntity taskEntity, String idUser) {
        UserEntity userEntityResult = userRepository.findById(idUser)
                .orElseThrow(() -> new UserNotFoundException(String.format("El id %s no corresponde a ningun usuario", idUser), HttpStatus.BAD_REQUEST));
        taskEntity.setUser(userEntityResult);
        if(!authService.currentUser().getId().equals(idUser)) {
            throw new UserNotFoundException("No estas autorizado para acceder a los recursos de otro usuario", HttpStatus.BAD_REQUEST);
        }
        try {
            return taskRepository.save(taskEntity);
        } catch (DataAccessException e) {
            throw new DatabaseOperationException(String.format("Error al intentar guardar la tarea del usuario con id %s", idUser));
        }
    }

    //Guardar una tarea, devuelve un TaskSummaryResponseDTO
    @Override
    @Transactional
    public TaskSummaryResponseDTO saveTask(TaskSaveRequestDTO taskSaveRequestDTO) {
        UserEntity userEntityResult = userRepository.findById(taskSaveRequestDTO.getIdUser())
                .orElseThrow(() -> new UserNotFoundException(String.format("El id %s no corresponde a ningun usuario", taskSaveRequestDTO.getIdUser()), HttpStatus.BAD_REQUEST));
        if(!authService.currentUser().getId().equals(taskSaveRequestDTO.getIdUser())) {
            throw new UserNotFoundException("No estas autorizado para acceder a los recursos de otro usuario", HttpStatus.BAD_REQUEST);
        }
        TaskEntity toSaveTaskEntity = TaskEntity.builder()
                .title(taskSaveRequestDTO.getTitle())
                .description(taskSaveRequestDTO.getDescription())
                .user(userEntityResult)
                .build();
        try {
            TaskEntity savedTaskEntity = taskRepository.save(toSaveTaskEntity);
            return TaskSummaryResponseDTO.builder()
                    .id(savedTaskEntity.getId())
                    .title(savedTaskEntity.getTitle())
                    .description(savedTaskEntity.getDescription())
                    .build();
        } catch (DataAccessException e) {
            throw new DatabaseOperationException(String.format("Error al intentar guardar la tarea del usuario con id %s", taskSaveRequestDTO.getIdUser()));
        }
    }

    //Actualizar una tarea, devuelve un TaskSummaryResponseDTO
    @Override
    @Transactional
    public TaskSummaryResponseDTO updateTask(TaskUpdateRequestDTO taskUpdateRequestDTO) {
        if (!userRepository.existsById(taskUpdateRequestDTO.getIdUser())) {
            throw new UserNotFoundException(String.format("El id %s no corresponde a ningun usuario", taskUpdateRequestDTO.getIdUser()), HttpStatus.BAD_REQUEST);
        }
        if(!authService.currentUser().getId().equals(taskUpdateRequestDTO.getIdUser())) {
            throw new UserNotFoundException("No estas autorizado para acceder a los recursos de otro usuario", HttpStatus.BAD_REQUEST);
        }
        TaskEntity taskEntityResult = taskRepository.findById(taskUpdateRequestDTO.getId())
                .orElseThrow(() -> new TaskNotFoundException(String.format("El id %s no corresponde a ninguna tarea", taskUpdateRequestDTO.getId()), HttpStatus.BAD_REQUEST));
        taskEntityResult.setTitle(taskUpdateRequestDTO.getTitle());
        taskEntityResult.setDescription(taskUpdateRequestDTO.getDescription());
        taskRepository.save(taskEntityResult);
        try {
            TaskEntity savedTaskEntity = taskRepository.save(taskEntityResult);
            return TaskSummaryResponseDTO.builder()
                    .id(savedTaskEntity.getId())
                    .title(savedTaskEntity.getTitle())
                    .description(savedTaskEntity.getDescription())
                    .build();
        } catch (DataAccessException e) {
            throw new DatabaseOperationException(String.format("Error al intentar actualizar la tarea del usuario con id %s", taskUpdateRequestDTO.getIdUser()));
        }
    }

    //Actualizar parcialmente una tarea, devuelve un TaskSummaryResponseDTO
    @Override
    @Transactional
    public TaskSummaryResponseDTO patchTask(TaskPatchRequestDTO taskPatchRequestDTO) {
        boolean isChanged = false;
        if (!userRepository.existsById(taskPatchRequestDTO.getIdUser())) {
            throw new UserNotFoundException(String.format("El id %s no corresponde a ningun usuario", taskPatchRequestDTO.getIdUser()), HttpStatus.BAD_REQUEST);
        }
        if(!authService.currentUser().getId().equals(taskPatchRequestDTO.getIdUser())) {
            throw new UserNotFoundException("No estas autorizado para acceder a los recursos de otro usuario", HttpStatus.BAD_REQUEST);
        }
        TaskEntity taskEntityResult = taskRepository.findById(taskPatchRequestDTO.getId())
                .orElseThrow(() -> new TaskNotFoundException(String.format("El id %s no corresponde a ninguna tarea", taskPatchRequestDTO.getId()), HttpStatus.BAD_REQUEST));
        if (taskPatchRequestDTO.getTitle() != null && !taskPatchRequestDTO.getTitle().isEmpty() && !taskPatchRequestDTO.getTitle().equals(taskEntityResult.getTitle())) {
            taskEntityResult.setTitle(taskPatchRequestDTO.getTitle());
            isChanged = true;
        }
        if (taskPatchRequestDTO.getDescription() != null && !taskPatchRequestDTO.getDescription().isEmpty() && !taskPatchRequestDTO.getDescription().equals(taskEntityResult.getDescription())) {
            taskEntityResult.setDescription(taskPatchRequestDTO.getDescription());
            isChanged = true;
        }
        if (!isChanged) {
            throw new TaskNotChangesException(String.format("No se realizaron cambios en la tarea con id %s", taskPatchRequestDTO.getId()), HttpStatus.BAD_REQUEST);
        }
        try {
            TaskEntity savedTaskEntity = taskRepository.save(taskEntityResult);
            return TaskSummaryResponseDTO.builder()
                    .id(savedTaskEntity.getId())
                    .title(savedTaskEntity.getTitle())
                    .description(savedTaskEntity.getDescription())
                    .build();
        } catch (DataAccessException e) {
            throw new DatabaseOperationException(String.format("Error al intentar actualizar la tarea del usuario con id %s", taskPatchRequestDTO.getIdUser()));
        }
    }

    //Eliminar una tarea por su id, devuelve un MessageResponseDTO
    @Override
    @Transactional
    public MessageResponseDTO deleteTaskById(String id) {
        Optional<TaskEntity> taskEntityResult = taskRepository.findById(id);
        if (taskEntityResult.isEmpty()) {
            throw new TaskNotFoundException(String.format("El id %s no corresponde a ninguna tarea", id), HttpStatus.BAD_REQUEST);
        }
        if (!authService.currentUser().getId().equals(taskEntityResult.get().getUser().getId())) {
            throw new UserNotFoundException("No estas autorizado para acceder a los recursos de otro usuario", HttpStatus.BAD_REQUEST);
        }
        try {
            taskRepository.deleteById(id);
            return MessageResponseDTO.builder()
                    .message(String.format("La tarea con id %s ha sido eliminada", id))
                    .build();
        } catch (DataAccessException e) {
            throw new DatabaseOperationException(String.format("Error al intentar eliminar la tarea con el id %s", id));
        }
    }

}
