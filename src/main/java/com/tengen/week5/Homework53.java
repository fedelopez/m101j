package com.tengen.week5;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Random;

/**
 * @author fede
 */
public class Homework53 {

    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient();

        DB db = client.getDB("test");
        DBCollection collection = db.getCollection("grades");
        collection.drop();

        Random random = new Random(4);
        for (int i = 0; i < 200; i++) {
            BasicDBObject document = new BasicDBObject("student_id", "student-" + i).append("class_id", Math.abs(random.nextInt(5) + 1));
            BasicDBObject score1 = new BasicDBObject("type", "exam").append("score", random.nextInt(100));
            BasicDBObject score2 = new BasicDBObject("type", "homework").append("score", random.nextInt(100));
            BasicDBObject score3 = new BasicDBObject("type", "homework").append("score", random.nextInt(100));
            BasicDBObject score4 = new BasicDBObject("type", "quiz").append("score", random.nextInt(100));
            document.append("scores", Arrays.asList(score1, score2, score3, score4));
            collection.insert(document);
        }


        DBObject document = collection.findOne();
        System.out.println("document = " + document);
    }
}
