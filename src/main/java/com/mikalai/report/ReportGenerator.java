package com.mikalai.report;

import com.mikalai.report.config.Configuration;
import com.mikalai.report.config.DynamicConfig;
import com.mikalai.report.mail.EmailService;
import com.mikalai.report.mail.TemplateService;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import java.time.LocalDate;


@Component
@Getter
@Setter
public class ReportGenerator {
    private static final Logger logger = LogManager.getLogger(ReportGenerator.class);
    public static final String DRAFT = "DRAFT:";

    @Autowired
    private TemplateService templateService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private Configuration config;

    public void sendDraftReport(DynamicConfig dynamicConfig){
        try {

            String body = templateService.getBody(dynamicConfig.getManager());

            String subject = DRAFT + String.format(config.getMailSubject(), LocalDate.now());

            sendReport(subject, body, dynamicConfig.getDraftMailTo(), dynamicConfig.getDraftMailCC());

            logger.info("Draft report was sent");
        } catch (Exception e) {
            logger.error(e);
        }
    }

    public void sendFinalReport(DynamicConfig dynamicConfig){
        try {

            String body = templateService.getBody(dynamicConfig.getManager());

            String subject = String.format(config.getMailSubject(), LocalDate.now());

            sendReport(subject, body, dynamicConfig.getFinalMailTo(), dynamicConfig.getFinalMailCC());

            logger.info("Final report was sent");
        } catch (Exception e) {
            logger.error(e);
        }
    }


    private void sendReport(String subject, String body, String to, String cc) throws Exception{
        Message email = emailService.createEmail(to, cc, subject, body);
        emailService.sendMessage(email);
    }

}
