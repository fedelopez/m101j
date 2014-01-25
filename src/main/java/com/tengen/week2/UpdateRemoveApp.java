package com.tengen.week2;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

/**
 * @author fede
 */
public class UpdateRemoveApp {
    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient();

        DB db = client.getDB("course");
        DBCollection collection = db.getCollection("updateRemove");
        collection.drop();

        List<String> names = Arrays.asList("bobby", "alice", "john", "amanda", "rachel");
        for (String name : names) {
            collection.insert(new BasicDBObject("_id", name));
        }

        collection.update(new BasicDBObject("_id", "alice"), new BasicDBObject("age", 24));
        collection.update(new BasicDBObject("_id", "alice"), new BasicDBObject("$set", new BasicDBObject("gender", "F")));

        collection.update(new BasicDBObject("_id", "frank"), new BasicDBObject("$set", new BasicDBObject("gender", "M")), true, false);
        collection.update(new BasicDBObject(), new BasicDBObject("$set", new BasicDBObject("title", "Dr")), false, true);

        collection.remove(new BasicDBObject("_id", "alice"));

        QueryBuilder builder = QueryBuilder.start();
        DBCursor cursor = collection.find(builder.get());
        while (cursor.hasNext()) {
            DBObject next = cursor.next();
            System.out.println(next.toString());
        }


        cursor.close();
    }
}
