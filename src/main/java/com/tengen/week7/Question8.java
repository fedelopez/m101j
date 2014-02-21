package com.tengen.week7;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

import java.io.IOException;

/**
 * How many animals are going to be inserted?
 *
 * @author fede
 */
public class Question8 {

    public static void main(String[] args) throws IOException {
        MongoClient c = new MongoClient();
        DB db = c.getDB("test");
        DBCollection animals = db.getCollection("animals");
        animals.drop();

        BasicDBObject animal = new BasicDBObject("animal", "monkey");

        animals.insert(animal);
        animal.removeField("animal");
        animal.append("animal", "cat");
        animals.insert(animal);
        animal.removeField("animal");
        animal.append("animal", "lion");
        animals.insert(animal);
    }
}
