package com.demo.sendgrid.dto;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotEmpty;

public class EmailRequestDTO {

    @NotEmpty(message = "INVALID_MAIL_TO")
    private String to;
    @NotEmpty(message = "INVALID_MAIL_FROM")
    private String from;
    @NotEmpty(message = "INVALID_MAIL_SUBJECT")
    private String subject;
    @NotEmpty(message = "INVALID_MAIL_TEMPLATE_NAME")
    private String templateName;
    private String content;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> queryParams = new HashMap<>();
    private Map<String, String> templateParams = new HashMap<>();

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(Map<String, String> queryParams) {
        this.queryParams = queryParams;
    }

    public Map<String, String> getTemplateParams() {
        return templateParams;
    }

    public void setTemplateParams(Map<String, String> templateParams) {
        this.templateParams = templateParams;
    }

    public static final class EmailRequestBuilder {
        private EmailRequestDTO emailRequest;

        private EmailRequestBuilder() {
            emailRequest = new EmailRequestDTO();
        }

        public static EmailRequestBuilder of() {
            return new EmailRequestBuilder();
        }

        public EmailRequestBuilder to(String to) {
            emailRequest.setTo(to);
            return this;
        }

        public EmailRequestBuilder from(String from) {
            emailRequest.setFrom(from);
            return this;
        }

        public EmailRequestBuilder subject(String subject) {
            emailRequest.setSubject(subject);
            return this;
        }

        public EmailRequestBuilder content(String content) {
            emailRequest.setContent(content);
            return this;
        }

        public EmailRequestBuilder templateName(String templateName) {
            emailRequest.setTemplateName(templateName);
            return this;
        }

        public EmailRequestBuilder headers(Map<String, String> headers) {
            emailRequest.setHeaders(headers);
            return this;
        }

        public EmailRequestBuilder queryParams(Map<String, String> queryParams) {
            emailRequest.setQueryParams(queryParams);
            return this;
        }

        public EmailRequestBuilder templateParams(Map<String, String> templateParams) {
            emailRequest.setTemplateParams(templateParams);
            return this;
        }

        public EmailRequestDTO build() {
            return emailRequest;
        }
    }
}
