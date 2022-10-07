package app.web.mohajeri.portfolio.bookstore.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exceptionHandler(Exception exception){
        log.warn("===Global Exception Handler : some error occurred",exception);
        return ResponseEntity.status(500).body(exception.getMessage());
    }

}
