package com.mikalai.report.mail;

import com.mikalai.report.spreadsheet.SpreadSheetReader;
import com.mikalai.report.config.Configuration;
import com.mikalai.report.entity.Record;
import com.mikalai.report.service.GoogleServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Properties;

public class TemplateService {
    private static final Logger logger = LogManager.getLogger(TemplateService.class);
    public static final String EMAIL_HTML = "email.html";
    public static final String MAIL_TEMPLATE_VM = "mail_template.vm";


    public TemplateService(SpreadSheetReader s) {
        this.s = s;

        Properties p = new Properties();
        p.setProperty("resource.loader", "class");
        p.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

        Velocity.init(p);

        template = Velocity.getTemplate(MAIL_TEMPLATE_VM);
    }

    private SpreadSheetReader s;
    private  Template template;


    public static void main(String[] args) throws Exception {
        TemplateService templateService = new TemplateService(new SpreadSheetReader(new GoogleServiceImpl()));
        String body = templateService.getBody();


        saveToFile(body);

    }

    public static void saveToFile(String body) throws Exception {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(EMAIL_HTML, false)));
            out.println(body);
            out.close();

    }

    public String getBody() throws Exception {
        List<Record> records = s.getRecords();
        List<String> plans = s.getPlans();


        VelocityContext context = new VelocityContext();

        context.put( "manager", Configuration.getValue("manager"));
        context.put("plans", plans );
        context.put("records", records);

        StringWriter sw = new StringWriter();
        template.merge( context, sw );


        return sw.toString();
    }
}
