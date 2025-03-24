package com.softhugordc.taskmanagerappv1.controller.handler;

import com.softhugordc.taskmanagerappv1.dto.response.ErrorResponseDTO;
import com.softhugordc.taskmanagerappv1.exception.*;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Controlador global de excepciones y errores
@ControllerAdvice
public class GlobalExceptionHandler {

    //Captura de excepcion por error de acceso a la base de datos
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<?> handleDataAccessException(DataAccessException e) {
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .error(String.format("Ocurrio un error al intentar acceder a la base de datos, %s", e.getMessage()))
                .build();
        return new ResponseEntity<>(errorResponseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //Captura de excepcion por error de conexion a la base de datos
    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<?> handleDatabaseConnectionException(ConnectException e) {
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .error(String.format("Ocurrio un error de conexion con la base de datos, %s", e.getMessage()))
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDTO);
    }

    //Captura de excepcion por error con la base de datos
    @ExceptionHandler(DatabaseOperationException.class)
    public ResponseEntity<?> handleDatabaseOperationException(DatabaseOperationException e) {
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .error(String.format("Ocurrio un error relacionado con la base de datos, %s", e.getMessage()))
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseDTO);
    }

    //Captura de excepcion por validaciones de entidades y dtos
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException e) {
        List<ErrorResponseDTO> errorResponseDTOS = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new ErrorResponseDTO(fieldError.getDefaultMessage()))
                .toList();
        Map<String, Object> responseErrorDTO = new HashMap<>();
        responseErrorDTO.put("errors", errorResponseDTOS);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseErrorDTO);
    }

    //Captura de excepcion por validacion de parametros de url
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e) {
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .error(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDTO);
    }

    //Captura de excepcion por tarea no encontrado
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<?> handleTaskNotFoundException(TaskNotFoundException e) {
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .error(e.getMessage())
                .build();
        return ResponseEntity.status(e.getHttpStatus()).body(errorResponseDTO);
    }

    //Captura de excepcion por usuario no encontrado
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e) {
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .error(e.getMessage())
                .build();
        return ResponseEntity.status(e.getHttpStatus()).body(errorResponseDTO);
    }

    //Captura de excepcion por usuario duplicado
    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<?> handleDuplicateUserException(DuplicateUserException e) {
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .error(e.getMessage())
                .build();
        return ResponseEntity.status(e.getHttpStatus()).body(errorResponseDTO);
    }

    @ExceptionHandler(TaskNotChangesException.class)
    public ResponseEntity<?> handleTaskNotChangesException(TaskNotChangesException e) {
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .error(e.getMessage())
                .build();
        return ResponseEntity.status(e.getHttpStatus()).body(errorResponseDTO);
    }

}

