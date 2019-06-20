package com.demo.sendgrid.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.demo.sendgrid.message.OutputMessage;

@ControllerAdvice
public class ExceptionController {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionController.class);

    @ExceptionHandler(InvalidEmailEntryException.class)
    public ResponseEntity<OutputMessage> exceptionHandler(InvalidEmailEntryException ex) {
        logger.error(ex.getMessage(), ex);
        OutputMessage output = getStandardOutput(ex);
        output.setErrorList(ex.getListErros());
        return new ResponseEntity<>(output, ex.getStatus());
    }

    private OutputMessage getStandardOutput(InvalidEmailEntryException ex) {
        OutputMessage output = new OutputMessage();
        output.setStatusCode(ex.getStatus().value());
        output.setMessage(ex.getMessage());
        return output;
    }
}
