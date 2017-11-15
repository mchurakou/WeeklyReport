package com.mikalai.report.service;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.sheets.v4.Sheets;

public interface GoogleService {
    Sheets getSheetsService() throws Exception;

    Gmail getGmailService() throws Exception;
}
