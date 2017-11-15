package com.mikalai.report.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.sheets.v4.Sheets;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Getter
@Setter
@Component
public class GoogleService {

    private static final Logger logger = LogManager.getLogger(GoogleService.class);
    /** Application name. */
    private static final String APPLICATION_NAME = "Weekly Report App";


    @Autowired
    private Credential credential;

    @Autowired
    private JsonFactory jsonFactory;

    @Autowired
    private HttpTransport httpTransport;

    /**
     * Build and return an authorized Sheets API client service.
     *
     * @return an authorized Sheets API client service
     * @throws IOException
     */
    public Sheets getSheetsService() throws Exception {
        return new Sheets.Builder(httpTransport, jsonFactory, credential).setApplicationName(APPLICATION_NAME).build();
    }

    /**
     * Build and return an authorized Gmail client service.
     * @return an authorized Gmail client service
     * @throws IOException
     */
    public Gmail getGmailService() throws Exception {
        return new Gmail.Builder(httpTransport, jsonFactory, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }


}
