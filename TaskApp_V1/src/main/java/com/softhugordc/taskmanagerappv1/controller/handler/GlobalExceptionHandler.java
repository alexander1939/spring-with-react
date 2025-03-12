package com.softhugordc.taskmanagerappv1.controller.handler;

import com.softhugordc.taskmanagerappv1.dto.error.ErrorDTO;
import com.softhugordc.taskmanagerappv1.exception.DatabaseOperationException;
import com.softhugordc.taskmanagerappv1.exception.TaskNotFoundException;
import com.softhugordc.taskmanagerappv1.exception.UserNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//Controlador global de excepciones y errores
@ControllerAdvice
public class GlobalExceptionHandler {

    //Captura de excepcion por erro de acceso a la base de datos
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<?> handleDataAccessException(DataAccessException e) {
        ErrorDTO responseErrorDTO = ErrorDTO.builder()
                .message("Ocurrio un error al intentar acceder a la base de datos ".concat(e.getMessage()))
                .build();
        return new ResponseEntity<>(responseErrorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //Captura de excepcion por error de conexion a la base de datos
    @ExceptionHandler(ConnectException.class)
    public ResponseEntity<?> handleDatabaseConnectionException(ConnectException e) {
        ErrorDTO responseErrorDTO = ErrorDTO.builder()
                .message("Ocurrio un error de conexion a la base de datos ".concat(e.getMessage()))
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseErrorDTO);
    }

    //Captura de excepcion por un error general
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGenericException(Exception e) {
        ErrorDTO responseErrorDTO = ErrorDTO.builder()
                .message("Ocurrio un error inesperado: ".concat(e.getMessage()))
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseErrorDTO);
    }

    //Captura de excepcion por error con la base de datos
    @ExceptionHandler(DatabaseOperationException.class)
    public ResponseEntity<?> handleDatabaseOperationException(DatabaseOperationException e) {
        ErrorDTO responseErrorDTO = ErrorDTO.builder()
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseErrorDTO);
    }

    //Captura de excepcion por validaciones de entidades y dtos
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException e) {
        List<ErrorDTO> errorDTOS = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new ErrorDTO(fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
        Map<String, Object> responseErrorDTO = new HashMap<>();
        responseErrorDTO.put("errors", errorDTOS);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseErrorDTO);
    }

    //Captura de excepcion por validacion de parametros de url
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e) {
        ErrorDTO responseErrorDTO = ErrorDTO.builder()
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseErrorDTO);
    }

    //Captura de excepcion por tarea no encontrado
    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<?> handleTaskNotFoundException(TaskNotFoundException e) {
        ErrorDTO responseErrorDTO = ErrorDTO.builder()
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(e.getStatus()).body(responseErrorDTO);
    }

    //Captura de excepcion por usuario no encontrado
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e) {
        ErrorDTO responseErrorDTO = ErrorDTO.builder()
                .message(e.getMessage())
                .build();
        return ResponseEntity.status(e.getStatus()).body(responseErrorDTO);
    }

}

