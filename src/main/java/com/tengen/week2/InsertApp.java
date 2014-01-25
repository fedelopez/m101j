package com.tengen.week2;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * @author fede
 */
public class InsertApp {

    public static void main(String[] args) throws Exception {
//        createCollection();
        quizJavaDriverInsert();
    }

    private static void createCollection() throws UnknownHostException {
        MongoClient client = new MongoClient();

        DB db = client.getDB("course");
        DBCollection collection = db.getCollection("insertTest");
        collection.drop();

        DBObject doc = new BasicDBObject().append("x", 1);
        DBObject doc2 = new BasicDBObject().append("x", 2);
        collection.insert(Arrays.asList(doc, doc2));
        collection.insert(Arrays.asList(doc, doc2));
    }

    public static void quizJavaDriverInsert() throws Exception {
        MongoClient client = new MongoClient();
        DB db = client.getDB("school");
        DBCollection people = db.getCollection("people");
        people.drop();

        DBObject doc = new BasicDBObject("name", "Andrew Erlichson")
                .append("company", "10gen");

        try {
            people.insert(doc);      // first insert
            doc.removeField("_id");  // remove the "_id" field
            people.insert(doc);      // second insert
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
