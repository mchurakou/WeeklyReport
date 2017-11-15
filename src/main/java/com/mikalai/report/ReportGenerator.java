package com.mikalai.report;

import com.mikalai.report.config.Configuration;
import com.mikalai.report.mail.EmailService;
import com.mikalai.report.mail.TemplateService;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.time.LocalDate;


@Component
@Getter
@Setter
public class ReportGenerator {
    private static final Logger logger = LogManager.getLogger(ReportGenerator.class);

    @Autowired
    private TemplateService templateService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private Configuration config;

    public void sendReport(){
        try {

            String body = templateService.getBody();

            String subject = String.format(config.getMailSubject(), LocalDate.now() );
            MimeMessage email = emailService.createEmail(config.getMailTo(), config.getMailFrom(), subject, body);
            emailService.sendMessage(config.getAccountId(), email);

            logger.info("Report was sent");
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
