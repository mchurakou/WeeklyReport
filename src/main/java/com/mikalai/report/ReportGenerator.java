package com.mikalai.report;

import com.mikalai.report.config.Configuration;
import com.mikalai.report.config.DynamicConfig;
import com.mikalai.report.mail.EmailService;
import com.mikalai.report.mail.TemplateService;
import com.mikalai.report.spreadsheet.SpreadSheetReader;
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
    public static final String DRAFT = "DRAFT:";

    @Autowired
    private TemplateService templateService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private Configuration config;


    @Autowired
    private SpreadSheetReader reader;

    public void sendDraftReport(){
        try {

            DynamicConfig dynamicConfig = reader.getConfig();
            String body = templateService.getBody(dynamicConfig.getManager());

            String subject = DRAFT + String.format(config.getMailSubject(), LocalDate.now());
            MimeMessage email = emailService.createEmail(dynamicConfig.getDraftMailTo(), dynamicConfig.getDraftMailCC(), config.getMailFrom(), subject, body);
            emailService.sendMessage(config.getAccountId(), email);

            logger.info("Report was sent");
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
