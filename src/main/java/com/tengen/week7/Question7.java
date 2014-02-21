package com.tengen.week7;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Your task is to write a program to remove every image from the images collection that appears in no album.
 * Or put another way, if an image does not appear in at least one album, it's an orphan and should be removed from the images collection.
 *
 * @author fede
 */
public class Question7 {

    private DBCollection images;
    private DBCollection albums;

    public Question7(DB db) {
        images = db.getCollection("images");
        albums = db.getCollection("albums");
    }


    public void cleanupImages() {
        Set<Object> toRemove = new HashSet<>();
        DBCursor cursor = images.find();
        int i = 0;
        long total = images.count();
        while (cursor.hasNext()) {
            DBObject image = cursor.next();
            int imageId = Integer.parseInt(image.get("_id").toString());
            QueryBuilder builder = QueryBuilder.start("images").in(Arrays.asList(imageId));
            int count = albums.find(builder.get()).count();
            if (count == 0) {
                toRemove.add(imageId);
            }
            if (i % 1000 == 0) {
                System.out.println("Processed " + i + " images, to process " + (total - i));
            }
            i++;
        }
        for (Object imageId : toRemove) {
            images.remove(new BasicDBObject("_id", imageId));
        }
    }

    public static void main(String[] args) throws UnknownHostException {
        MongoClient client = new MongoClient();
        DB exam = client.getDB("exam");
        Question7 question7 = new Question7(exam);
        question7.cleanupImages();
    }

}
