package com.softhugordc.taskmanagerappv1.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

//Excepcion personalizada por tarea sin cambios
@Data
public class TaskNotChangesException extends RuntimeException {

    private HttpStatus httpStatus;

    public TaskNotChangesException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

}
