package com.mikalai.report.mail;

import com.google.api.services.gmail.model.Message;

import javax.mail.internet.MimeMessage;

public interface EmailService {
    Message sendMessage(String userId,
            MimeMessage emailContent) throws Exception;

    MimeMessage createEmail(String to, String cc,
            String from,
            String subject,
            String bodyText) throws Exception;
}
