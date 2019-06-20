package com.demo.sendgrid.service;

import org.springframework.stereotype.Component;

import com.demo.sendgrid.dto.EmailRequestDTO;
import com.demo.sendgrid.dto.EmailResponseDTO;
import com.demo.sendgrid.exception.EmailConnectionException;

@Component
public interface EmailClientComponent {

    EmailResponseDTO send(EmailRequestDTO request) throws EmailConnectionException;

}
