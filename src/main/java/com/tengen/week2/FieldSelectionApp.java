package com.tengen.week2;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Random;

/**
 * @author fede
 */
public class FieldSelectionApp {
    public static void main(String[] args) throws UnknownHostException {
        DBCursor cursor = null;
        try {
            MongoClient client = new MongoClient();

            DB db = client.getDB("course");
            DBCollection collection = db.getCollection("fieldSelectionTest");
            collection.drop();

            Random random = new Random();

            for (int i = 0; i < 100; i++) {
                DBObject doc = new BasicDBObject().append("x", random.nextInt(2)).append("y", random.nextInt(100)).append("z", random.nextInt(1000));
                collection.insert(doc);
            }

            DBObject query = new BasicDBObject("x", 0).append("y", new BasicDBObject("$gt", 39).append("$lt", 50));
            QueryBuilder builder = QueryBuilder.start("x").is(0).and("y").greaterThan(10).lessThan(70);

            cursor = collection.find(query, new BasicDBObject("y", true).append("_id", false));
//            cursor = collection.find(builder.get(), new BasicDBObject("y", true));
            while (cursor.hasNext()) {
                DBObject next = cursor.next();
                System.out.println(next.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }


    }
}
