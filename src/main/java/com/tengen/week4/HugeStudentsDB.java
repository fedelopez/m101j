package com.tengen.week4;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Random;

/**
 * @author fede
 */
public class HugeStudentsDB {

    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient();

        DB db = client.getDB("test");
        DBCollection collection = db.getCollection("students");

        Random random = new Random(4);
        for (int i = 0; i < 10000000; i++) {
            DBObject document = new BasicDBObject("name", "student-" + i).append("postalCode", Math.abs(random.nextInt(9999)));
            collection.insert(document);
        }


        DBObject document = collection.findOne();
        System.out.println("document = " + document);
    }
}
