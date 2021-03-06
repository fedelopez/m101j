package com.tengen.week2;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Random;

/**
 * @author fede
 */
public class DotNotationApp {
    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient();

        DB db = client.getDB("course");
        DBCollection collection = db.getCollection("fieldSelectionTest");
        collection.drop();

        Random random = new Random();

        for (int i = 0; i < 100; i++) {
            BasicDBObject start = new BasicDBObject("x", random.nextInt(100)).append("y", random.nextInt(90) + 10);
            BasicDBObject end = new BasicDBObject("x", random.nextInt(100)).append("y", random.nextInt(90) + 10);
            DBObject doc = new BasicDBObject("_id", i).append("start", start).append("end", end);
            collection.insert(doc);
        }

        QueryBuilder builder = QueryBuilder.start("start.x").greaterThan(50);
        DBCursor cursor = collection.find(builder.get(), new BasicDBObject("start.f", true));
        while (cursor.hasNext()) {
            DBObject next = cursor.next();
            System.out.println(next.toString());
        }

        DBObject one = collection.findOne(builder.get(), new BasicDBObject("start.f", true));
        System.out.println("one = " + one);

        cursor.close();
    }
}
