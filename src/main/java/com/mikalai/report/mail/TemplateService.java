package com.mikalai.report.mail;

import com.mikalai.report.config.Configuration;
import com.mikalai.report.entity.Record;
import com.mikalai.report.spreadsheet.SpreadSheetReader;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Properties;


@Component
@Getter
@Setter
public class TemplateService {
    private static final Logger logger = LogManager.getLogger(TemplateService.class);
    public static final String EMAIL_HTML = "email.html";
    public static final String MAIL_TEMPLATE_VM = "mail_template.vm";

    @Autowired
    private Configuration config;

    public TemplateService() {

        Properties p = new Properties();
        p.setProperty("resource.loader", "class");
        p.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

        Velocity.init(p);

        template = Velocity.getTemplate(MAIL_TEMPLATE_VM);
    }

    @Autowired
    private SpreadSheetReader s;

    private  Template template;


    public static void saveToFile(String body) throws Exception {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(EMAIL_HTML, false)));
            out.println(body);
            out.close();

    }

    public String getBody() throws Exception {
        List<Record> records = s.getRecords();
        List<String> plans = s.getPlans();


        VelocityContext context = new VelocityContext();

        context.put("manager", config.getManager());
        context.put("plans", plans );
        context.put("records", records);
        context.put("jiraUrl", config.getJiraUrl());

        StringWriter sw = new StringWriter();
        template.merge( context, sw );

        return sw.toString();
    }
}
