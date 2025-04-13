package ru.spb.itmo.asashina.yaprofessionaltask2.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.spb.itmo.asashina.yaprofessionaltask2.exception.EntityAlreadyExistsException;
import ru.spb.itmo.asashina.yaprofessionaltask2.exception.EntityDoesNotExistException;

import java.util.stream.Collectors;

@Hidden
@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public ResponseEntity<?> methodArgumentNotValidExceptionHandler(BindException e) {
        return ResponseEntity.badRequest()
                .body(
                e.getBindingResult().getFieldErrors()
                        .stream()
                        .collect(Collectors.toMap(
                                FieldError::getField,
                                DefaultMessageSourceResolvable::getDefaultMessage,
                                (message1, message2) -> message1 + ", " + message2
                        )));
    }

    @ExceptionHandler({EntityDoesNotExistException.class})
    public ResponseEntity<?> notFoundExceptionHandler(Exception e) {
        return ResponseEntity.status(404)
                .body(e.getMessage());
    }

    @ExceptionHandler({EntityAlreadyExistsException.class})
    public ResponseEntity<?> conflictExceptionHandler(Exception e) {
        return ResponseEntity.status(409)
                .body(e.getMessage());
    }

    @ExceptionHandler({IllegalStateException.class})
    public ResponseEntity<?> badRequestExceptionHandler(Exception e) {
        return ResponseEntity.status(400)
                .body(e.getMessage());
    }

}
