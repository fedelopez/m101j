package com.tengen.week1;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;

/**
 * @author fede
 */
public class HelloWorldMongodbStyle {

    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient();

        DB db = client.getDB("course");
        DBCollection hello = db.getCollection("hello");

        DBObject document = hello.findOne();
        System.out.println("document = " + document);

        db = client.getDB("food");
        hello = db.getCollection("fruits");
        System.out.println("hello = " + hello.count());
    }
}
