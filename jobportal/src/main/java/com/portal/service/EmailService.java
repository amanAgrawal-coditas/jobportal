package com.portal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService
{
    @Autowired
    private JavaMailSender javaMailSender;
    public void sendEmail(String toEmail,String body,String subject)
    {

        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom("aman.agrawal@coditas.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);
        javaMailSender.send(message);
    }
}
