package io.cjf.jinterviewback.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@EnableAutoConfiguration
public class MailUtil {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${from.email}")
    private String fromEmail;

    public void mailSend2(String toEmail, String subject, String text){
        final SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

}
