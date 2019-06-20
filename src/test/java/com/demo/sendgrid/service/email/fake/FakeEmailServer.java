package com.demo.sendgrid.service.email.fake;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.demo.sendgrid.base.EmailBaseIT;
import com.demo.sendgrid.dto.EmailRequestDTO;
import com.demo.sendgrid.dto.EmailResponseDTO;
import com.demo.sendgrid.dto.EmailResponseDTO.EmailResponseBuilder;
import com.demo.sendgrid.service.email.fake.FakeMessage.FakeMessageBuilder;

@Component
public class FakeEmailServer {

    private static final String MIME_TYPE_HTML = "text/html;";
    private static final int TIMEOUT = 25000;

    public List<FakeMessage> getInbox() {
        EmailBaseIT.getGreenMail().waitForIncomingEmail(TIMEOUT, 5);
        MimeMessage[] emails = EmailBaseIT.getGreenMail().getReceivedMessages();
        return Stream.of(emails).map(e -> getMessage(e))
                .collect(Collectors.toList());
    }

    public EmailResponseDTO send(EmailRequestDTO requestEmail) throws Exception {
        Message message = buildMessage(requestEmail);
        Transport.send(message);
        return buildResponse(requestEmail.getContent());
    }

    private FakeMessage getMessage(MimeMessage message) {
        try {
            return FakeMessageBuilder.of()
                    .from(addressToString(message.getFrom()))
                    .recipients(addressToString(message.getAllRecipients()))
                    .subject(message.getSubject())
                    .content(getContent((MimeMultipart) message.getContent()))
                    .build();
        } catch (MessagingException | IOException e) {
            return null;
        }
    }

    private String getContent(MimeMultipart mimeMultipart) throws MessagingException, IOException {
        if (mimeMultipart.getCount() > 0) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(0);
            return bodyPart.getContent().toString().replaceAll("\\r\\n?", "\n");
        }
        return "";
    }

    private List<String> addressToString(Address[] from) {
        return Stream.of(from).map(f -> f.toString())
                .collect(Collectors.toList());
    }

    private MimeMessage buildMessage(EmailRequestDTO requestEmail) throws Exception {
        Session session = EmailBaseIT.getGreenMail().getSmtp().createSession();
        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(requestEmail.getContent(), MIME_TYPE_HTML);
        MimeMessage message = new MimeMessage(session);
        Multipart multiPart = new MimeMultipart("alternative");
        multiPart.addBodyPart(htmlPart);
        message.setContent(multiPart);
        message.setFrom(new InternetAddress());
        message.setRecipients(RecipientType.TO, InternetAddress.parse(requestEmail.getTo(), false));
        message.setSubject(requestEmail.getSubject());
        message.setSentDate(new Date());
        return message;
    }

    private EmailResponseDTO buildResponse(String content) {
        return EmailResponseBuilder.of()
                .statusCode(HttpStatus.OK.value())
                .body(content).build();
    }

    public FakeMessage getEmailFromInbox(String emailAccount) {
        return getInbox()
                .stream()
                .filter(email -> email.getRecipients().size() > 0 && email.getRecipients().get(0).equals(emailAccount))
                .findFirst()
                .orElse(null);

    }
}
