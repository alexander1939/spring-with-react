package com.softhugordc.taskmanagerappv1.service;

import com.softhugordc.taskmanagerappv1.domain.UserEntity;
import com.softhugordc.taskmanagerappv1.dto.request.RegisterRequestDTO;
import com.softhugordc.taskmanagerappv1.dto.response.UserResponseDTO;
import com.softhugordc.taskmanagerappv1.exception.DatabaseOperationException;
import com.softhugordc.taskmanagerappv1.exception.DuplicateUserException;
import com.softhugordc.taskmanagerappv1.exception.UserNotFoundException;
import com.softhugordc.taskmanagerappv1.repository.IUserRepository;
import com.softhugordc.taskmanagerappv1.security.service.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

//Servicio para hacer operaciones con usuarios
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //Buscar un usuario por su id, devuelve un Optional de UserEntity
    @Override
    public Optional<UserEntity> findById(String id) {
        return Optional.ofNullable(userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format("El id %s no corresponde a ningun usuario", id), HttpStatus.BAD_REQUEST)));
    }

    //Buscar un usuario por su email, devuelve un Optional de UserEntity
    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(String.format("El email %s no se encuentra registrado", email), HttpStatus.BAD_REQUEST)));
    }

    //Comprobar si un usuario existe por su email, devuelve un boleano
    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    //Guardar un usuario, devuelve un UserEntity
    @Override
    @Transactional
    public UserEntity save(UserEntity userEntity) {
        if (existsByEmail(userEntity.getEmail())) {
            throw new DuplicateUserException(String.format("El email %s ya esta registrado", userEntity.getEmail()), HttpStatus.BAD_REQUEST);
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

    //Crear un usuario, devuelve un UserReponseDTO
    @Override
    @Transactional
    public UserResponseDTO saveUser(RegisterRequestDTO registerRequestDTO) {
        if (existsByEmail(registerRequestDTO.getEmail())) {
            throw new DuplicateUserException(String.format("El email %s ya esta registrado", registerRequestDTO.getEmail()), HttpStatus.BAD_REQUEST);
        }
        UserEntity toSaveUserEntity = UserEntity.builder()
                .name(registerRequestDTO.getName())
                .lastName(registerRequestDTO.getLastName())
                .email(registerRequestDTO.getEmail())
                .password(passwordEncoder.encode(registerRequestDTO.getPassword()))
                .build();
        try {
            UserEntity savedUserEntity = userRepository.save(toSaveUserEntity);
            return UserResponseDTO.builder()
                    .id(savedUserEntity.getId())
                    .name(savedUserEntity.getName())
                    .lastName(savedUserEntity.getLastName())
                    .email(savedUserEntity.getEmail())
                    .build();
        } catch (DataAccessException e) {
            throw new DatabaseOperationException("Error al intentar registrar el usuario");
        }
    }

}
