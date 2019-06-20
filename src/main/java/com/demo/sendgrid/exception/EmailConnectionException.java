package com.demo.sendgrid.exception;

import java.io.IOException;

public class EmailConnectionException extends IOException {

    private static final long serialVersionUID = 1L;

    public EmailConnectionException(Exception e) {
        super(e);
    }
}
