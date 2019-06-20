package com.demo.sendgrid.client;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.demo.sendgrid.dto.EmailRequestDTO;
import com.demo.sendgrid.dto.EmailResponseDTO;
import com.demo.sendgrid.dto.EmailResponseDTO.EmailResponseBuilder;
import com.demo.sendgrid.dto.RequestSendGridBuilder;
import com.demo.sendgrid.exception.EmailConnectionException;
import com.demo.sendgrid.service.EmailClientComponent;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

@Component
public class SendGridClient implements EmailClientComponent {

    private SendGrid sendGrid;
    private String sendGridKey;

    public SendGridClient(SendGrid sendGrid, @Value("${sendgrid.key}") String sendGridKey) {
        this.sendGrid = sendGrid;
        this.sendGridKey = sendGridKey;
    }

    @Override
    public EmailResponseDTO send(EmailRequestDTO request) throws EmailConnectionException {
        try {
            Request sendGridRequest = RequestSendGridBuilder.of()
                    .content(request.getContent())
                    .subject(request.getSubject())
                    .to(request.getTo())
                    .from(request.getFrom())
                    .key(sendGridKey)
                    .build();
            Response sendGridResponse = sendGrid.api(sendGridRequest);
            return getResponse(sendGridResponse, sendGridRequest);
        } catch (IOException e) {
            throw new EmailConnectionException(e);
        }
    }

    private EmailResponseDTO getResponse(Response response, Request request) {
        if (response.getBody().isEmpty()) {
            response.setBody(request.getBody());
        }
        return EmailResponseBuilder.of()
                .body(response.getBody())
                .headers(response.getHeaders())
                .statusCode(response.getStatusCode())
                .build();
    }
}
