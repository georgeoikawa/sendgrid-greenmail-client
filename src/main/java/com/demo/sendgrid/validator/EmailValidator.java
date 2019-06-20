package com.demo.sendgrid.validator;

import com.demo.sendgrid.dto.EmailRequestDTO;
import com.demo.sendgrid.exception.InvalidEmailEntryException;

public interface EmailValidator {

    void validateEntry(EmailRequestDTO request) throws InvalidEmailEntryException;

}