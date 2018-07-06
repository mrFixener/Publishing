package com.task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Dimon on 02.07.2018.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFound extends RuntimeException {
    public ResourceNotFound() {
        super();
    }

    public ResourceNotFound(String message) {
        super(message);
    }

    public ResourceNotFound(String idName, final Long idModel) {
        super(idName + " " + idModel + " not found");
    }

    public ResourceNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
