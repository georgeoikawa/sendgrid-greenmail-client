package com.demo.sendgrid.service.email.fake;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.demo.sendgrid.dto.EmailRequestDTO;
import com.demo.sendgrid.dto.EmailResponseDTO;
import com.demo.sendgrid.exception.EmailConnectionException;
import com.demo.sendgrid.service.EmailClientComponent;

@Component
@Primary
public class FakeEmailClient implements EmailClientComponent {

    @Autowired
    private FakeEmailServer fakeEmailServer;

    @Override
    public EmailResponseDTO send(EmailRequestDTO request) throws EmailConnectionException {
        try {
            return fakeEmailServer.send(request);
        } catch (Exception e) {
            throw new EmailConnectionException(e);
        }
    }

}
