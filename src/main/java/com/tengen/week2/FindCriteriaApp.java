package com.tengen.week2;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Random;

/**
 * @author fede
 */
public class FindCriteriaApp {
    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient();

        DB db = client.getDB("course");
        DBCollection collection = db.getCollection("findCriteriaTest");
        collection.drop();

        for (int i = 0; i < 1000; i++) {
            DBObject doc = new BasicDBObject().append("x", new Random().nextInt(2)).append("y", new Random().nextInt(100));
            collection.insert(doc);
        }

//        DBObject query = new BasicDBObject("x", 0).append("y", new BasicDBObject("$gt", 39).append("$lt", 50));
        QueryBuilder builder = QueryBuilder.start("x").is(0).and("y").greaterThan(10).lessThan(90);

        long count = collection.count(builder.get());
        System.out.println("count = " + count);

        DBCursor cursor = collection.find(builder.get());
        while (cursor.hasNext()) {
            DBObject next = cursor.next();
            System.out.println(next);
        }

        cursor.close();

    }
}
