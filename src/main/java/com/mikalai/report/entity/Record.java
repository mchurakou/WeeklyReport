package com.mikalai.report.entity;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Record {
    private String jiraNumber;
    private String jiraUrl;
    private String jiraTitle;
    private String status;
}
