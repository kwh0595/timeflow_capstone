package com.capstone.timeflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class SendingMailService {
    @Autowired
    private MailSender mailSender;

    public void sendEmail(String toAddress, String subject, String msgBody) {
        SimpleMailMessage smm = new SimpleMailMessage();
        smm.setTo(toAddress);
        smm.setSubject(subject);
        smm.setText(msgBody);

        mailSender.send(smm);
    }
}