package com.mikalai.report;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.sheets.v4.Sheets;
import com.mikalai.report.config.DynamicConfig;
import com.mikalai.report.entity.NotificationType;
import com.mikalai.report.service.CredentialService;
import com.mikalai.report.service.GoogleService;
import com.mikalai.report.spreadsheet.SpreadSheetReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties
public class Application {
    public static final String SPECIFY_MODE_DRAFT_FINAL = "specify mode: draft/final";

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        ReportGenerator reportGenerator = context.getBean(ReportGenerator.class);
        SpreadSheetReader reader = context.getBean(SpreadSheetReader.class);
        DynamicConfig dynamicConfig = reader.getConfig();

        if (args.length == 0){
            throw new RuntimeException(SPECIFY_MODE_DRAFT_FINAL);
        }

        String mode = args[0];

        switch (NotificationType.valueOf(mode.toUpperCase())){
            case DRAFT:
                reportGenerator.sendDraftReport(dynamicConfig);
                break;
            case FINAL:
                reportGenerator.sendFinalReport(dynamicConfig);
                break;
            default:
                throw new RuntimeException(SPECIFY_MODE_DRAFT_FINAL);


        }


    }


    @Autowired
    private CredentialService credentialService;

    @Autowired
    private GoogleService googleService;


    @Bean
    public Sheets sheetsService() throws Exception{
        return googleService.getSheetsService();
    }

    @Bean
    public Gmail getGmailService() throws Exception{
        return googleService.getGmailService();
    }

    @Bean
    public JsonFactory jsonFactory(){
        return JacksonFactory.getDefaultInstance();
    }

    @Bean
    public HttpTransport httpTransport() throws Exception {
        return GoogleNetHttpTransport.newTrustedTransport();
    }

    @Bean
    public Credential credential() throws Exception {
        return credentialService.authorize();
    }




}
