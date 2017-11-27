package com.mikalai.report.mail;


import javax.mail.Message;

public interface EmailService {
    void sendMessage(Message emailContent) throws Exception;

    Message createEmail(String to, String cc,
            String subject,
            String bodyText) throws Exception;
}
