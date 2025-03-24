package com.softhugordc.taskmanagerappv1.service;

import com.softhugordc.taskmanagerappv1.domain.TaskEntity;
import com.softhugordc.taskmanagerappv1.dto.request.TaskSaveRequestDTO;
import com.softhugordc.taskmanagerappv1.dto.request.TaskPatchRequestDTO;
import com.softhugordc.taskmanagerappv1.dto.request.TaskUpdateRequestDTO;
import com.softhugordc.taskmanagerappv1.dto.response.MessageResponseDTO;
import com.softhugordc.taskmanagerappv1.dto.response.TaskResponseDTO;
import com.softhugordc.taskmanagerappv1.dto.response.TaskSummaryResponseDTO;

import java.util.List;
import java.util.Optional;

//Interfaz que define los metodos para interactuar con la entidad TaskEntity
public interface ITaskService {

    Optional<TaskEntity> findById(String id);

    TaskResponseDTO findByIdTask(String id);

    List<TaskEntity> findAllByIdUser(String idUser);

    List<TaskSummaryResponseDTO> findAllTasksByIdUser(String idUser);

    List<TaskEntity> findAll();

    List<TaskResponseDTO> findAllTasks();

    TaskEntity save(TaskEntity taskEntity, String idUser);

    TaskSummaryResponseDTO saveTask(TaskSaveRequestDTO taskSaveRequestDTO);

    TaskSummaryResponseDTO updateTask(TaskUpdateRequestDTO taskUpdateRequestDTO);

    TaskSummaryResponseDTO patchTask(TaskPatchRequestDTO taskPatchRequestDTO);

    MessageResponseDTO deleteTaskById(String id);


}
