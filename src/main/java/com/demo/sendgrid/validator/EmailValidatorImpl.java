package com.demo.sendgrid.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.sendgrid.dto.EmailRequestDTO;
import com.demo.sendgrid.exception.InvalidEmailEntryException;
import com.demo.sendgrid.message.IMessage;
import com.demo.sendgrid.message.MessageInfo;

@Service
public class EmailValidatorImpl implements EmailValidator {

    @Autowired
    private IMessage serviceMessage;

    @Override
    public void validateEntry(final EmailRequestDTO request) throws InvalidEmailEntryException {
        List<MessageInfo> messages = new ArrayList<>();
        validateRequiredFields(request, messages, this.serviceMessage);
        if (!messages.isEmpty()) {
            throw new InvalidEmailEntryException(serviceMessage.mergeMessages(messages),
                    this.serviceMessage.getMessage("INVALID_ENTRIES_EMAIL"));
        }
    }

    private void validateRequiredFields(final Object object, final List<MessageInfo> errorList,
            final IMessage message) {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        final Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);
        for (final ConstraintViolation<Object> constraintViolation : constraintViolations) {
            final MessageInfo mensagem = message.getMessageInfo(constraintViolation.getMessage());
            errorList.add(mensagem);
        }
    }
}
