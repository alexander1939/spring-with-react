package com.softhugordc.taskmanagerappv1.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

//Excepcion personalizada para tarea no encontrada
@Data
public class TaskNotFoundException extends RuntimeException {

    private HttpStatus httpStatus;

    public TaskNotFoundException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

}
