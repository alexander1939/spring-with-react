package com.softhugordc.taskmanagerappv1.exception;

import lombok.Data;

//Excepcion personalizada para operaciones con la base de datos
@Data
public class DatabaseOperationException extends RuntimeException {

    public DatabaseOperationException(String message) {
        super(message);
    }

}
