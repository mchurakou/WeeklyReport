package com.mikalai.report.spreadsheet;

import com.google.api.services.sheets.v4.model.*;
import com.google.api.services.sheets.v4.Sheets;
import com.mikalai.report.config.DynamicConfig;
import com.mikalai.report.entity.Record;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class SpreadSheetReader {
    private static final Logger logger = LogManager.getLogger(SpreadSheetReader.class);
    public static final String SPREADSHEET_ID = "19rQdF8XohhyiC_DHOTFBQYhnSfMhBL5hlOM5FoBM6lI";
    public static final String JIRAS_RANGE = "Report tab!A2:C";
    public static final String PLANS_RANGE = "Plans!A:A";
    public static final String CONDUCTED_MEETINGS_RANGE = "Conducted meetings!A:A";
    public static final String CONFIGURATION_RANGE = "Configuration!B:B";

    @Autowired
    private Sheets sheetsService;

    public List<Record> getRecords() throws Exception {

        ValueRange response = sheetsService.spreadsheets().values().get(SPREADSHEET_ID, JIRAS_RANGE).execute();
        List<List<Object>> values = response.getValues();
        List<Record> result;
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

    public List<String> getPlans() throws Exception {
        return getRows(PLANS_RANGE);
    }


    public List<String> getConductedMeetings() throws Exception {
        return getRows(CONDUCTED_MEETINGS_RANGE);
    }

    public List<String> getRows(String range) throws java.io.IOException {
        ValueRange response = sheetsService.spreadsheets().values().get(SPREADSHEET_ID, range).execute();
        List<List<Object>> values = response.getValues();
        List<String> result;
        if (values == null || values.size() == 0) {
            throw new RuntimeException("No data!");
        } else {
            result = values.stream().map(row -> (String) row.get(0)).collect(Collectors.toList());

        }

        return result;
    }

    public DynamicConfig getConfig() throws Exception {
        ValueRange response = sheetsService.spreadsheets().values().get(SPREADSHEET_ID, CONFIGURATION_RANGE).execute();
        List<List<Object>> values = response.getValues();
        DynamicConfig result;
        if (values == null || values.size() == 0) {
            throw new RuntimeException("No data!");
        } else {
            String draftMailTo = (String)values.get(0).get(0);
            String draftMailCC = (String)values.get(1).get(0);
            String finalMailTo = (String)values.get(2).get(0);
            String finalMailCC = (String)values.get(3).get(0);
            String manager = (String)values.get(4).get(0);
            result = new DynamicConfig(draftMailTo, draftMailCC, finalMailTo, finalMailCC, manager);

        }

        return result;

    }

}
