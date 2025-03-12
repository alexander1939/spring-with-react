package com.softhugordc.taskmanagerappv1.exception;

import lombok.Data;

@Data
public class DatabaseOperationException extends RuntimeException {

    public DatabaseOperationException(String message) {
        super(message);
    }

}
