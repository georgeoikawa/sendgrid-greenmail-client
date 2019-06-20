package com.demo.sendgrid.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.demo.sendgrid.dto.EmailRequestDTO;
import com.demo.sendgrid.dto.EmailRequestDTO.EmailRequestBuilder;
import com.demo.sendgrid.dto.EmailResponseDTO;
import com.demo.sendgrid.exception.EmailConnectionException;
import com.demo.sendgrid.validator.EmailValidator;

@Service
public class EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
    private EmailClientComponent emailClientComponent;
    private TemplateService templateService;

    public EmailService(EmailClientComponent emailClientComponent
            , EmailValidator emailValidatorImpl
            , TemplateService templateService) {
        this.emailClientComponent = emailClientComponent;
        this.templateService = templateService;
    }

    public EmailResponseDTO sendMail(EmailRequestDTO email) throws Exception {
        try {
            String parsedTemplate = templateService.parseTemplateParams(email);
            EmailRequestDTO request = EmailRequestBuilder.of()
                    .content(parsedTemplate)
                    .from(email.getFrom())
                    .to(email.getTo())
                    .subject(email.getSubject())
                    .templateName(email.getTemplateName())
                    .build();
            EmailResponseDTO response = emailClientComponent.send(request);
            return response;
        } catch (EmailConnectionException e) {
            LOGGER.error("Error on send email process", e);
            throw e;
        }
    }
}
