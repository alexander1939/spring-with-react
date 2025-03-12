package com.softhugordc.taskmanagerappv1.service;

import com.softhugordc.taskmanagerappv1.domain.TaskEntity;
import com.softhugordc.taskmanagerappv1.dto.response.TaskSummaryResponseDTO;

import java.util.List;
import java.util.Optional;

//Interfaz que define los metodos para interactuar con la entidad TaskEntity
public interface ITaskService {

    Optional<TaskEntity> findById(String id);

    boolean existsById(String id);

    List<TaskEntity> findByIdUser(String idUser);

    List<TaskSummaryResponseDTO> findAllTasks();

    TaskEntity save(TaskEntity taskEntity);

    TaskSummaryResponseDTO saveTask(TaskEntity taskEntity);

    void deleteTaskById(String id);

}
