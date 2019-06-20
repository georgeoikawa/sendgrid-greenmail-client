package com.demo.sendgrid.service.email.fake;

import java.util.List;

public class FakeMessage {

    private List<String> recipients;
    private String content;
    private List<String> from;
    private String subject;

    public List<String> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<String> recipients) {
        this.recipients = recipients;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getFrom() {
        return from;
    }

    public void setFrom(List<String> from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public static final class FakeMessageBuilder {
        private FakeMessage fakeMessage;

        private FakeMessageBuilder() {
            fakeMessage = new FakeMessage();
        }

        public static FakeMessageBuilder of() {
            return new FakeMessageBuilder();
        }

        public FakeMessageBuilder recipients(List<String> recipients) {
            fakeMessage.setRecipients(recipients);
            return this;
        }

        public FakeMessageBuilder content(String content) {
            fakeMessage.setContent(content);
            return this;
        }

        public FakeMessageBuilder from(List<String> from) {
            fakeMessage.setFrom(from);
            return this;
        }

        public FakeMessageBuilder subject(String subject) {
            fakeMessage.setSubject(subject);
            return this;
        }

        public FakeMessage build() {
            return fakeMessage;
        }
    }
}
