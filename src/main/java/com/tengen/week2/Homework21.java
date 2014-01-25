package com.tengen.week2;

import com.mongodb.*;

import java.net.UnknownHostException;

/**
 * @author fede
 */
public class Homework21 {

    //Now it’s your turn to analyze the data set.
    //Find all exam scores greater than or equal to 65. and sort those scores from lowest to highest.
    //What is the student_id of the lowest exam score above 65?
    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient();

        DB db = client.getDB("students");
        DBCollection collection = db.getCollection("grades");

        QueryBuilder builder = QueryBuilder.start("score").greaterThanEquals(65);
        DBCursor cursor = collection.find(builder.get()).sort(new BasicDBObject("score", 1)).limit(10);

        while (cursor.hasNext()) {
            DBObject next = cursor.next();
            System.out.println(next.toString());
        }


        cursor.close();
    }
}
