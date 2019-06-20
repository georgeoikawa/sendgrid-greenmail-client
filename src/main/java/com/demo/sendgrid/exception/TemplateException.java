package com.demo.sendgrid.exception;

public class TemplateException extends Exception {

    private static final long serialVersionUID = 1L;

    public TemplateException(String code) {
        super(code);
    }

    public TemplateException(String code, Throwable cause) {
        super(code, cause);
    }
}
