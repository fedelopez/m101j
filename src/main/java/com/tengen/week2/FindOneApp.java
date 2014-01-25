package com.tengen.week2;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Random;

/**
 * @author fede
 */
public class FindOneApp {
    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient();

        DB db = client.getDB("course");
        DBCollection collection = db.getCollection("findTest");
        collection.drop();

        for (int i = 0; i < 10; i++) {
            DBObject doc = new BasicDBObject().append("x", new Random().nextInt(100));
            collection.insert(doc);
        }

        DBObject one = collection.findOne();
        System.out.println("one = " + one);

        DBCursor cursor = collection.find();
        while (cursor.hasNext()) {
            DBObject next = cursor.next();
            System.out.println(next);
        }

        long count = collection.count();
        System.out.println("count = " + count);

        cursor.close();

    }
}
