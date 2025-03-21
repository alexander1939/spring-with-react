package com.softhugordc.taskmanagerappv1.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

//Excepcion personalizada para usuario no encontrado
@Data
public class UserNotFoundException extends RuntimeException {

    private HttpStatus httpStatus;

    public UserNotFoundException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

}
