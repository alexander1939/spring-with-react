package com.softhugordc.taskmanagerappv1.service;

import com.softhugordc.taskmanagerappv1.domain.UserEntity;

import java.util.Optional;

//Interfaz que define los metodos para interactuar con la entidad UserEntity
public interface IUserService {

    Optional<UserEntity> findById(String id);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByIdTask(String idTask);

    UserEntity save(UserEntity user);

}
