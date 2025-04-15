package fr.eni.cave.api;

import fr.eni.cave.bll.BllException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionManager{
     @ExceptionHandler(value = {BllException.class})
     public ResponseEntity<String> handleBllException(BllException e) {
         return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
     }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<String> handleValidationError(MethodArgumentNotValidException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
