package com.demo.sendgrid.api.v1.controller;

import java.util.HashMap;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.demo.sendgrid.base.EmailBaseIT;
import com.demo.sendgrid.dto.EmailRequestDTO;
import com.demo.sendgrid.dto.EmailRequestDTO.EmailRequestBuilder;
import com.demo.sendgrid.service.email.fake.FakeEmailServer;

public class EmailSendgridControllerIT extends EmailBaseIT {

    @Autowired
    private FakeEmailServer fakeEmailServer;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void whenSendMailThenVerifyImbox() throws Exception {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        Map<String, String> queryParams = new HashMap<>();

        Map<String, String> templateParams = new HashMap<>();
        templateParams.put("name", "User Test");
        templateParams.put("email", "user.test@mail.com");

        EmailRequestDTO requestEmail = EmailRequestBuilder.of()
                .to("user.test@mail.com")
                .from("server@mail.com")
                .content("content")
                .headers(headers)
                .queryParams(queryParams)
                .templateName("email-demo-template")
                .templateParams(templateParams)
                .subject("E-mail Confirmation")
                .build();
        fakeEmailServer.send(requestEmail);
        Assertions.assertThat(fakeEmailServer.getInbox().size()).isEqualTo(1);
    }
}
