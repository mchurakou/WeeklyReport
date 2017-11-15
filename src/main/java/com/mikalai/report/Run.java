package com.mikalai.report;

import com.google.api.services.gmail.Gmail;
import com.mikalai.report.config.Configuration;
import com.mikalai.report.mail.EmailService;
import com.mikalai.report.mail.EmailServiceImpl;
import com.mikalai.report.mail.TemplateService;
import com.mikalai.report.service.GoogleService;
import com.mikalai.report.service.GoogleServiceImpl;
import com.mikalai.report.spreadsheet.SpreadSheetReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.internet.MimeMessage;
import java.time.LocalDate;

public class Run {

    private static final Logger logger = LogManager.getLogger(Run.class);
        public static void main(String[] args) throws Exception {
            GoogleService googleService = new GoogleServiceImpl();
            Gmail gmailService = googleService.getGmailService();

            TemplateService templateService = new TemplateService(new SpreadSheetReader(googleService));


            String userId = Configuration.getValue("account.id");

            EmailService emailService = new EmailServiceImpl(gmailService);

            String body = templateService.getBody();

            String subject = String.format(Configuration.getValue("subject"), LocalDate.now() );
            MimeMessage email = emailService.createEmail(Configuration.getValue("sent.to"), Configuration.getValue("sent.from"), subject, body);
            emailService.sendMessage(userId, email);

            logger.info("Report was sent");
        }



}
