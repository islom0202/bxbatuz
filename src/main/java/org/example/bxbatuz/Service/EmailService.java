package org.example.bxbatuz.Service;

import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final String from = "turgunpolatovislom5@gmail.com";

    public void sendEmailToEmployee(String email, String password){
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(email);
            message.setSubject("Credentials for bxbatuz");
            message.setText("Login: " + email + "\nPassword: " + password);

            javaMailSender.send(message);
    }

}
