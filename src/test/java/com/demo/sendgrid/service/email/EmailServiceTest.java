package com.demo.sendgrid.service.email;

import java.io.IOException;
import java.util.Collections;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.demo.sendgrid.dto.EmailRequestDTO;
import com.demo.sendgrid.dto.EmailRequestDTO.EmailRequestBuilder;
import com.demo.sendgrid.dto.EmailResponseDTO;
import com.demo.sendgrid.dto.EmailResponseDTO.EmailResponseBuilder;
import com.demo.sendgrid.exception.TemplateException;
import com.demo.sendgrid.service.EmailClientComponent;
import com.demo.sendgrid.service.EmailService;
import com.demo.sendgrid.service.TemplateService;
import com.demo.sendgrid.validator.EmailValidator;

public class EmailServiceTest {

    private EmailService emailService;
    @Mock
    private EmailClientComponent emailClientComponent;
    @Mock
    private TemplateService templateService;
    @Mock
    private EmailValidator emailValidator;

    @Before
    public void init() throws TemplateException, IOException {
        MockitoAnnotations.initMocks(this);
        emailService = new EmailService(emailClientComponent
                , emailValidator
                , templateService);
        Mockito.when(templateService.parseTemplateParams(Mockito.any(EmailRequestDTO.class)))
                .thenReturn(TemplateServiceTest.getTemplateString("result-email-demo-template"));
    }

    @Test
    public void whenCallClientThenSendEmail() throws Exception {
        EmailRequestDTO email = EmailRequestBuilder.of()
                .to("john@company.com")
                .build();
        Mockito.when(emailClientComponent.send(Mockito.any(EmailRequestDTO.class))).thenReturn(
         EmailResponseBuilder.of()
                    .headers(Collections.singletonMap("a", "b"))
                    .body("456")
                    .build());
        EmailResponseDTO response = emailService.sendMail(email);
        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getHeaders()).isEqualTo(Collections.singletonMap("a", "b"));
        Assertions.assertThat(response.getStatusCode()).isEqualTo(0);
        Assertions.assertThat(response.getBody()).isEqualTo("456");
        Mockito.verify(templateService).parseTemplateParams(email);
    }


}
