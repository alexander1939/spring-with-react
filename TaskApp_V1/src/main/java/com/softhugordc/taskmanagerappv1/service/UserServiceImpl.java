package com.softhugordc.taskmanagerappv1.service;

import com.softhugordc.taskmanagerappv1.domain.UserEntity;
import com.softhugordc.taskmanagerappv1.dto.response.UserResponseDTO;
import com.softhugordc.taskmanagerappv1.exception.DatabaseOperationException;
import com.softhugordc.taskmanagerappv1.exception.DuplicateUserException;
import com.softhugordc.taskmanagerappv1.exception.UserNotFoundException;
import com.softhugordc.taskmanagerappv1.repository.IUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

//Servicio para hacer operaciones con usuarios
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //Buscar un usuario por su id
    @Override
    public Optional<UserEntity> findById(String id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if (userEntity.isEmpty()) {
            throw new UserNotFoundException(String.format("El id %s no corresponde a ningun usuario", id), HttpStatus.NOT_FOUND);
        }
        return userEntity;
    }

    //Buscar un usuario por su email
    @Override
    public Optional<UserEntity> findByEmail(String email) {
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        if (userEntity.isEmpty()) {
            throw new UserNotFoundException(String.format("El email %s no se encuentra registrado", email), HttpStatus.NOT_FOUND);
        }
        return userEntity;
    }

    //Comprobar si un usuario existe
    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    //Crear un usuario
    @Override
    @Transactional
    public UserEntity save(UserEntity userEntity) {
        if (existsByEmail(userEntity.getEmail())) {
            throw new DuplicateUserException(String.format("El email %s ya esta registrado",userEntity.getEmail()), HttpStatus.CONFLICT);
        }
        UserEntity toSaveUserEntity = UserEntity.builder()
                .name(userEntity.getName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .password(passwordEncoder.encode(userEntity.getPassword()))
                .build();
        try {
            return userRepository.save(toSaveUserEntity);
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Error al intentar registrar el usuario");
        }
    }

}
