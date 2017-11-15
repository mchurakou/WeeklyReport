package com.mikalai.report;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.sheets.v4.Sheets;
import com.mikalai.report.service.CredentialService;
import com.mikalai.report.service.GoogleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties
public class Application {
    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        ReportGenerator reportGenerator = context.getBean(ReportGenerator.class);
        reportGenerator.sendReport();
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
