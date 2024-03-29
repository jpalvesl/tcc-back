package com.tcc.joaomyrlla.appcode2know.service.impl;

import com.tcc.joaomyrlla.appcode2know.service.IEmailService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailServiceImpl implements IEmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Value("${support.mail}")
    private String supportMail;

    @Override
    public void sendEmailToClient(String subject, String content) {
        try {
            MimeMessage mail = mailSender.createMimeMessage();

            MimeMessageHelper message = new MimeMessageHelper(mail);
            message.setFrom(supportMail);
            message.setTo(supportMail);
            message.setSubject(subject);
            message.setText(content);

            mailSender.send(mail);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
