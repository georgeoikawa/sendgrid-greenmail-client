package com.demo.sendgrid.service.email;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.demo.sendgrid.client.SendGridClient;
import com.demo.sendgrid.dto.EmailRequestDTO;
import com.demo.sendgrid.dto.EmailResponseDTO;
import com.demo.sendgrid.dto.EmailRequestDTO.EmailRequestBuilder;
import com.demo.sendgrid.exception.EmailConnectionException;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

public class SendGridClientTest {

    private static final int STATUS_CODE = 205;
    private static final String HTML = "<!doctype html><html>"
            + "<body class=\"\"><table border=\"0\" cellpadding=\"0\" "
            + "cellspacing=\"0\" class=\"body\"></table></body></html>";

    private SendGridClient sendGridClient;
    @Mock
    private SendGrid sendGrid;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        sendGridClient = new SendGridClient(sendGrid, "sendGridKey");
    }

    @Test
    public void testReceiveEmailWhenResponseIsEmpty() throws Exception {
        Response responseSendGrid = new Response();
        when(sendGrid.api(Mockito.any(Request.class))).thenReturn(responseSendGrid);
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        responseSendGrid.setHeaders(headers);
        responseSendGrid.setStatusCode(STATUS_CODE);
        responseSendGrid.setBody("");
        EmailResponseDTO responseEmail = sendGridClient.send(createRequestEmail());
        ArgumentCaptor<Request> requestCapture = ArgumentCaptor.forClass(Request.class);
        verify(sendGrid, atLeastOnce()).api(requestCapture.capture());
        Request request = requestCapture.getValue();
        assertThat(responseEmail.getBody()).isEqualTo(responseSendGrid.getBody());
        assertThat(responseEmail.getStatusCode()).isEqualTo(STATUS_CODE);
        assertThat(responseEmail.getHeaders()).containsKey("Content-Type");
        assertThat(request.getEndpoint()).isEqualTo("mail/send");
        assertThat(request.getMethod().toString()).isEqualTo("POST");
        assertThat(request.getBody()).isEqualTo("{\"from\":{\"email\":\"server@mail.com\"},"
                + "\"subject\":\"E-mail confirmation\",\"personalizations\":[{\"to\":[{\"email\":\"user.test@mail.com\"}]}],"
                + "\"content\":[{\"type\":\"text/html\",\"value\":\"Hello\"}],"
                + "\"custom_args\":{\"customerAccountNumber\":\"sendGridKey\"}}");
    }

    @Test
    public void testReceiveEmail() throws Exception {
        Response responseSendGrid = new Response();
        when(sendGrid.api(Mockito.any(Request.class))).thenReturn(responseSendGrid);
        responseSendGrid.setHeaders(Collections.singletonMap("Content-Type", "application/json"));
        responseSendGrid.setStatusCode(STATUS_CODE);
        responseSendGrid.setBody(HTML);
        EmailRequestDTO requestEmail = createRequestEmail();
        EmailResponseDTO responseEmail = sendGridClient.send(requestEmail);
        ArgumentCaptor<Request> requestCapture = ArgumentCaptor.forClass(Request.class);
        verify(sendGrid, atLeastOnce()).api(requestCapture.capture());
        Request request = requestCapture.getValue();
        assertThat(responseEmail.getBody()).isEqualTo(HTML);
        assertThat(responseEmail.getStatusCode()).isEqualTo(STATUS_CODE);
        assertThat(responseEmail.getHeaders().containsKey("Content-Type")).isTrue();
        assertThat(request.getEndpoint()).isEqualTo("mail/send");
        assertThat(request.getMethod().toString()).isEqualTo("POST");
        assertThat(request.getBody()).isEqualTo("{\"from\":{\"email\":\"server@mail.com\"},"
                + "\"subject\":\"E-mail confirmation\",\"personalizations\":[{\"to\":[{\"email\":\"user.test@mail.com\"}]}],"
                + "\"content\":[{\"type\":\"text/html\",\"value\":\"Hello\"}],"
                + "\"custom_args\":{\"customerAccountNumber\":\"sendGridKey\"}}");
    }

    @Test
    public void whenSendMailWithInvalidKeyThenReturnEmailException() throws Exception {
        when(sendGrid.api(Mockito.any(Request.class))).thenThrow(IOException.class);
        EmailConnectionException throwable = (EmailConnectionException) catchThrowable(() -> sendGridClient.send(createRequestEmail()));
        ArgumentCaptor<Request> requestCapture = ArgumentCaptor.forClass(Request.class);
        verify(sendGrid, atLeastOnce()).api(requestCapture.capture());
        assertThat(throwable).isExactlyInstanceOf(EmailConnectionException.class);
    }

    private EmailRequestDTO createRequestEmail() {
        String emailTo = "user.test@mail.com";
        String subject = "E-mail confirmation";
        return EmailRequestBuilder.of()
                .from("server@mail.com")
                .to(emailTo)
                .subject(subject)
                .content("Hello")
                .build();
    }
}
