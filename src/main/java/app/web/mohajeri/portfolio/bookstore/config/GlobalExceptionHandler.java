package app.web.mohajeri.portfolio.bookstore.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    public String exceptionHandler(Exception exception){
        log.warn("===Global Exception Handler : some error occurred",exception);
        return exception.getMessage();
    }

}
