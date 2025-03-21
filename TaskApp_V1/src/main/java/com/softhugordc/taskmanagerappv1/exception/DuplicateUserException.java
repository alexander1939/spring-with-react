package com.softhugordc.taskmanagerappv1.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

//Excepcion personalizada por usuario ya registrado
@Data
public class DuplicateUserException extends RuntimeException {

    private HttpStatus HttpStatus;

    public DuplicateUserException(String message, HttpStatus httpStatus) {
        super(message);
        this.HttpStatus = httpStatus;
    }

}
