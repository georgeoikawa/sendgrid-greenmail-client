package com.demo.sendgrid.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.demo.sendgrid.message.MessageInfo;

public class InvalidEmailEntryException extends Exception {

    private static final long serialVersionUID = 1L;
    private HttpStatus httpStatus;

    private final List<MessageInfo> listErros = new ArrayList<>();

    public InvalidEmailEntryException(final List<MessageInfo> listErros, final String message) {
        super(message);
        this.httpStatus = HttpStatus.BAD_REQUEST;
        addErrors(listErros);
    }

    public List<MessageInfo> getListErros() {
        return listErros;
    }

    public void addErrors(final List<MessageInfo> errors) {
        listErros.addAll(errors);
    }

    public void addError(final MessageInfo error) {
        listErros.add(error);
    }

    public HttpStatus getStatus() {
        return httpStatus;
    }

    public void setStatus(HttpStatus status) {
        this.httpStatus = status;
    }
}
