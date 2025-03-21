package com.softhugordc.taskmanagerappv1.service;

import com.softhugordc.taskmanagerappv1.domain.TaskEntity;
import com.softhugordc.taskmanagerappv1.dto.request.TaskRequestDTO;
import com.softhugordc.taskmanagerappv1.dto.response.TaskResponseDTO;

import java.util.List;
import java.util.Optional;

//Interfaz que define los metodos para interactuar con la entidad TaskEntity
public interface ITaskService {

    Optional<TaskEntity> findById(String id);

    List<TaskEntity> findAllByIdUser(String idUser);

    List<TaskResponseDTO> findAllTasksByIdUser(String idUser);

    List<TaskEntity> findAll();

    TaskEntity save(TaskEntity taskEntity, String idUser);

    TaskResponseDTO save(TaskRequestDTO taskRequestDTO, String idUser);

    void deleteTaskById(String id);

}
