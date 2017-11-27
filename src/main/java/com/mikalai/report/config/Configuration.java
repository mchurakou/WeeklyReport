package com.mikalai.report.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "app.report")
@Getter
@Setter
@Component
public class Configuration {

    private String mailFrom;
    private String mailPassword;
    private String mailSubject;
    private String jiraUrl;
    private String smtpPort;
    private String smtpHost;

}
