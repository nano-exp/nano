package nano.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class ErrorAdvice {

  @ExceptionHandler(NoResourceFoundException.class)
  public String onResource404() {
    return "forward:/";
  }
}
