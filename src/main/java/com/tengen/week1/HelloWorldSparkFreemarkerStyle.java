package com.tengen.week1;

import freemarker.template.Configuration;
import freemarker.template.Template;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fede
 */
public class HelloWorldSparkFreemarkerStyle {

    public static void main(String[] args) throws Exception {
        final Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(HelloWorldFreemarkerStyle.class, "/");


        Spark.get(new Route("/") {
            @Override
            public Object handle(Request request, Response response) {
                try {
                    Template template = configuration.getTemplate("index.html");
                    StringWriter stringWriter = new StringWriter();
                    Map<String, Object> map = new HashMap<>();
                    map.put("blah", "Freemarker!!");

                    template.process(map, stringWriter);
                    return stringWriter.toString();
                } catch (Exception e) {
                    return e.getLocalizedMessage();
                }
            }
        });

    }

}
