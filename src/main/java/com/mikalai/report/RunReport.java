package com.mikalai.report;

import com.mikalai.report.entity.Record;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class RunReport {

    private static final Logger logger = LogManager.getLogger(RunReport.class);
    public static void main(String[] args) throws Exception {
        SpreadSheetReader s = new SpreadSheetReader(new GoogleServiceImpl());
        List<Record> records = s.getRecords();
        System.out.println(records);

    }
}
