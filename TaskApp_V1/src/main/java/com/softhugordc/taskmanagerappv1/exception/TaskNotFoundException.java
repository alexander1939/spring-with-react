package com.softhugordc.taskmanagerappv1.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class TaskNotFoundException extends RuntimeException {

    private final HttpStatus status;

    public TaskNotFoundException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

}
