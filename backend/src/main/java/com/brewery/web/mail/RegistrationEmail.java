package com.brewery.web.mail;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class RegistrationEmail {
    private final ObjectProvider<JavaMailSender> sender;
    private final String from;

    public RegistrationEmail(ObjectProvider<JavaMailSender> sender,
                             @Value("${CRM_REGISTRATION_FROM:${spring.mail.username:}}") String from) {
        this.sender = sender;
        this.from = from;
    }

    public void send(String recipient, String link) {
        JavaMailSender mailSender = sender.getIfAvailable();
        if (mailSender == null || from.isBlank()) {
            throw new IllegalStateException("Registration mail requires an SMTP host and sender address");
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(recipient);
        message.setSubject("Verify your CRM account");
        message.setText("""
                Complete your CRM registration and set your password using this link:

                %s

                The link expires in 24 hours. If you weren't expecting this email, you can ignore it.
                """.formatted(link));
        mailSender.send(message);
    }
}
