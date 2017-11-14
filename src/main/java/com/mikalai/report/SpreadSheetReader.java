package com.mikalai.report;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.*;
import com.google.api.services.sheets.v4.Sheets;
import com.mikalai.report.entity.Record;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SpreadSheetReader {
    private static final Logger logger = LogManager.getLogger(SpreadSheetReader.class);




    public static final String SPREADSHEET_ID = "19rQdF8XohhyiC_DHOTFBQYhnSfMhBL5hlOM5FoBM6lI";
    public static final String RANGE = "Report tab!A2:C";

    public SpreadSheetReader(GoogleService googleService) {
        this.googleService = googleService;
    }

    private GoogleService googleService;






    public List<Record> getRecords() throws Exception {
        Sheets service = googleService.getSheetsService();

        ValueRange response = service.spreadsheets().values().get(SPREADSHEET_ID, RANGE).execute();
        List<List<Object>> values = response.getValues();
        List<Record> result = null;
        if (values == null || values.size() == 0) {
            throw new RuntimeException("No data!");
        } else {
            result = values.stream().map(row -> {
                String jiraNumber = (String) row.get(0);
                String jiraTitle = (String) row.get(1);
                String status = (String) row.get(2);
                Record record = new Record(jiraNumber, jiraTitle, status);
                return record;
            }).collect(Collectors.toList());

        }

        return result;
    }

}
