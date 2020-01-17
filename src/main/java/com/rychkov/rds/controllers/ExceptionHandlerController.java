package com.rychkov.rds.controllers;

import com.rychkov.rds.dtos.ResponseDto;
import com.rychkov.rds.exceptions.DataObjectException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
@ControllerAdvice
class ExceptionHandlerController {
    @ExceptionHandler(DataObjectException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseDto handleException(DataObjectException e) {
        return ResponseDto.builder().error(true).message(e.getMessage()).build();
    }
}