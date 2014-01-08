package com.tengen;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fede
 */
public class HelloWorldFreemarkerStyle {

    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(HelloWorldFreemarkerStyle.class, "/");

        Template template = configuration.getTemplate("index.html");

        StringWriter stringWriter = new StringWriter();

        Map<String, Object>  map = new HashMap<>();
        map.put("name", "Freemarker!!");

        template.process(map, stringWriter);

        System.out.println("map = " + stringWriter);


    }

}
