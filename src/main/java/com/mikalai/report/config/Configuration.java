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
    private String manager;
    private String accountId;
    private String mailTo;
    private String mailFrom;
    private String mailSubject;


}
