package com.mikalai.report.spreadsheet;

import com.google.api.services.sheets.v4.model.*;
import com.google.api.services.sheets.v4.Sheets;
import com.mikalai.report.entity.Record;
import com.mikalai.report.service.GoogleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class SpreadSheetReader {
    private static final Logger logger = LogManager.getLogger(SpreadSheetReader.class);




    public static final String SPREADSHEET_ID = "19rQdF8XohhyiC_DHOTFBQYhnSfMhBL5hlOM5FoBM6lI";
    public static final String JIRAS_RANGE = "Report tab!A2:C";
    public static final String PLANS_RANGE = "Plans!A:A";

    public SpreadSheetReader(GoogleService googleService) {
        this.googleService = googleService;
    }

    private GoogleService googleService;






    public List<Record> getRecords() throws Exception {
        Sheets service = googleService.getSheetsService();

        ValueRange response = service.spreadsheets().values().get(SPREADSHEET_ID, JIRAS_RANGE).execute();
        List<List<Object>> values = response.getValues();
        List<Record> result;
        if (values == null || values.size() == 0) {
            throw new RuntimeException("No data!");
        } else {
            result = values.stream().map(row -> {
                String jiraUrl = (String) row.get(0);
                String[] split = jiraUrl.split("/");
                String jiraNumber = split[split.length - 1];
                String jiraTitle = (String) row.get(1);
                String status = (String) row.get(2);
                Record record = new Record(jiraNumber, jiraUrl, jiraTitle, status);
                return record;
            }).collect(Collectors.toList());

        }

        return result;
    }

    public List<String> getPlans() throws Exception {
        Sheets service = googleService.getSheetsService();

        ValueRange response = service.spreadsheets().values().get(SPREADSHEET_ID, PLANS_RANGE).execute();
        List<List<Object>> values = response.getValues();
        List<String> result;
        if (values == null || values.size() == 0) {
            throw new RuntimeException("No data!");
        } else {
            result = values.stream().map(row -> (String) row.get(0)).collect(Collectors.toList());

        }

        return result;
    }

}
