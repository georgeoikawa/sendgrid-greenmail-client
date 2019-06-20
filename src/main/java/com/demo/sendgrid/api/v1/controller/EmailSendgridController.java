package com.demo.sendgrid.api.v1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.sendgrid.dto.EmailRequestDTO;
import com.demo.sendgrid.dto.EmailResponseDTO;
import com.demo.sendgrid.service.EmailService;
import com.demo.sendgrid.validator.EmailValidator;

@RestController
@RequestMapping("/api/send")
public class EmailSendgridController {

    @Autowired
    private EmailValidator emailValidator;

    @Autowired
    private EmailService emailService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody EmailRequestDTO request)
            throws Exception {
        emailValidator.validateEntry(request);
        EmailResponseDTO response = emailService.sendMail(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
