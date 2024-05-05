package ru.nsu.fit.pupynin.webhelper.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("nsuwebhelper@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }

    public void sendErrmsgEmail(String from, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("nsuwebhelper@gmail.com");
        message.setFrom(from);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }

}
