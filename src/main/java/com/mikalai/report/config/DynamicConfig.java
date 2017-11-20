package com.mikalai.report.config;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DynamicConfig {

    private String draftMailTo;
    private String draftMailCC;

    private String finalMailTo;
    private String finalMailCC;
    private String manager;
}
