package com.tengen.week1;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import freemarker.template.Configuration;
import freemarker.template.Template;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        final Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(HelloWorldFreemarkerStyle.class, "/");

        Spark.get(new Route("/") {
            @Override
            public Object handle(Request request, Response response) {
                try {
                    Template template = configuration.getTemplate("index.html");
                    StringWriter stringWriter = new StringWriter();
                    template.process(getUser(new MongoClient()), stringWriter);
                    return stringWriter.toString();
                } catch (Exception e) {
                    return e.getLocalizedMessage();
                }
            }
        });

        Spark.get(new Route("/echo/:thing") {
            @Override
            public Object handle(Request request, Response response) {
                return request.params(":thing");
            }
        });

        Spark.get(new Route("/fruit_survey") {
            @Override
            public Object handle(Request request, Response response) {
                try {
                    Template template = configuration.getTemplate("fruits.html");
                    StringWriter stringWriter = new StringWriter();
                    DBCollection fruits = getFruits(new MongoClient());//todo, how to make it happen from the MongoDB?
                    Map<String, List<String>> fruitsMap = Collections.singletonMap("fruits", Arrays.asList("bananas", "oranges", "pears"));
                    template.process(fruitsMap, stringWriter);
                    return stringWriter.toString();
                } catch (Exception e) {
                    return e.getLocalizedMessage();
                }
            }
        });

        Spark.post(new Route("/favourite_fruit") {
            @Override
            public Object handle(Request request, Response response) {
                return request.queryParams("fruit");
            }
        });
    }

    private static DBObject getUser(MongoClient client) {
        DB db = client.getDB("course");
        DBCollection hello = db.getCollection("hello");
        return hello.findOne();
    }

    private static DBCollection getFruits(MongoClient client) {
        DB db = client.getDB("food");
        return db.getCollection("fruits");
    }
}
