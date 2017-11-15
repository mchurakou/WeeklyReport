package com.mikalai.report.config;

import com.mikalai.report.mail.TemplateService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {

    private static final Logger logger = LogManager.getLogger(TemplateService.class);
    private static Properties prop = new Properties();
    static {
        String filename = "prop.properties";
        InputStream input = Configuration.class.getClassLoader().getResourceAsStream(filename);
        try {
            prop.load(input);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    public static String getValue(String key){
        return (String) prop.get(key);
    }
}
