package com.mikalai.report.mail;

import com.mikalai.report.config.Configuration;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


@Service
@Getter
@Setter
public class EmailServiceImpl implements EmailService {

    @Autowired
    private Configuration configuration;
    private static final Logger logger = LogManager.getLogger(EmailServiceImpl.class);
    private static final String TEXT_HTML_CHARSET_UTF_8 = "text/html; charset=utf-8";


    public void sendMessage(
            Message message) throws Exception {

        Transport.send(message);
        logger.info("Message was sent");
    }


    /**
     * Create a MimeMessage using the parameters provided.
     *
     * @param to email address of the receiver
     * @param subject subject of the email
     * @param bodyText body text of the email
     * @return the MimeMessage to be used to send email
     * @throws MessagingException
     */
    public Message createEmail(String to,
            String cc,
            String subject,
            String bodyText) throws Exception {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", configuration.getSmtpHost());
        props.put("mail.smtp.port", configuration.getSmtpPort());

        Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(configuration.getMailFrom(), configuration.getMailPassword());
                }
            });

        MimeMessage email = new MimeMessage(session);

        email.setFrom(new InternetAddress(configuration.getMailFrom()));
        email.addRecipients(javax.mail.Message.RecipientType.TO,
                to);
        if (Strings.isNotEmpty(cc)) {
            email.addRecipients(javax.mail.Message.RecipientType.CC, cc);
        }
        email.setSubject(subject);
        email.setContent(bodyText, TEXT_HTML_CHARSET_UTF_8);
        return email;
    }






}
