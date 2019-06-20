package com.demo.sendgrid.dto;

import java.util.Map;
import java.util.Objects;

public class EmailResponseDTO {

    private int statusCode;
    private String body;
    private Map<String, String> headers;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmailResponseDTO)) {
            return false;
        }
        EmailResponseDTO that = (EmailResponseDTO) o;
        return statusCode == that.statusCode
                && Objects.equals(body, that.body)
                && Objects.equals(headers, that.headers);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(statusCode, body, headers);
    }

    @Override
    public String toString() {
        return "EmailResponse{"
                + "statusCode=" + statusCode
                + ", body='" + body + '\''
                + ", headers=" + headers
                + '}';
    }

    public static final class EmailResponseBuilder {
        private EmailResponseDTO emailResponse;

        private EmailResponseBuilder() {
            emailResponse = new EmailResponseDTO();
        }

        public static EmailResponseBuilder of() {
            return new EmailResponseBuilder();
        }

        public EmailResponseBuilder statusCode(int statusCode) {
            emailResponse.setStatusCode(statusCode);
            return this;
        }

        public EmailResponseBuilder body(String body) {
            emailResponse.setBody(body);
            return this;
        }

        public EmailResponseBuilder headers(Map<String, String> headers) {
            emailResponse.setHeaders(headers);
            return this;
        }

        public EmailResponseDTO build() {
            return emailResponse;
        }
    }
}
