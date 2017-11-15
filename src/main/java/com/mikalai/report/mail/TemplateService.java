package com.mikalai.report.mail;

import com.mikalai.report.SpreadSheetReader;
import com.mikalai.report.entity.Record;
import com.mikalai.report.service.GoogleServiceImpl;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Properties;

public class TemplateService {
    public static void main(String[] args) throws Exception {

        SpreadSheetReader s = new SpreadSheetReader(new GoogleServiceImpl());
        List<Record> records = s.getRecords();

        List<String> plans = s.getPlans();



        Properties p = new Properties();
        p.setProperty("resource.loader", "class");
        p.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

        Velocity.init(p);


        VelocityContext context = new VelocityContext();

        context.put( "manager", "Dorien" );


        context.put("plans", plans );


        context.put("records", records);

        Template template = Velocity.getTemplate("mail_template.vm");


        StringWriter sw = new StringWriter();
        template.merge( context, sw );



        try {
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("email.html", false)));
            out.println(sw.toString());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(sw.toString());
    }
}
