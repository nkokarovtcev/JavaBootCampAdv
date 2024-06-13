package com.nox.JavaBootCampAdv.controller;

import com.nox.JavaBootCampAdv.dto.BadRequestExceptionDto;
import com.nox.JavaBootCampAdv.dto.ControllerExceptionDto;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice(basePackages = "com.nox.JavaBootCampAdv.controller")
@SuppressWarnings("unused")
@Profile("prod")
public class GeneralControllerExceptionHandler {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<BadRequestExceptionDto> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, List<String>> body = new HashMap<>();
        BadRequestExceptionDto badRequestExceptionDto = new BadRequestExceptionDto();
        badRequestExceptionDto.setMessage("Request is not valid");

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
        badRequestExceptionDto.setDetails(errors);
        return new ResponseEntity<>(badRequestExceptionDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ResponseEntity<ControllerExceptionDto> handleControllerException(Exception e) {
        ControllerExceptionDto controllerExceptionDto = new ControllerExceptionDto("We are sorry, but something went wrong. Please, try again later");
        return ResponseEntity.internalServerError().body(controllerExceptionDto);
    }
}
