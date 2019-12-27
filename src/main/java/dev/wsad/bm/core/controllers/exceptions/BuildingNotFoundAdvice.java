package dev.wsad.bm.core.controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class BuildingNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(BuildingNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String employeeNotFoundHandler(BuildingNotFoundException ex) {
        return ex.getMessage();
    }

}
