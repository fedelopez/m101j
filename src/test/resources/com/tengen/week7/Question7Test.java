package com.tengen.week7;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * @author fede
 */
public class Question7Test {

    private DBCollection images;
    private DBCollection albums;
    private DB db;

    @Before
    public void setup() throws UnknownHostException {
        MongoClient client = new MongoClient();

        db = client.getDB("test");
        images = db.getCollection("images");
        albums = db.getCollection("albums");

        images.drop();
        albums.drop();
    }

    @Test
    public void cleanupImages() {

        images.insert(new BasicDBObject("_id", 1).append("height", "480").append("width", "640"));
        images.insert(new BasicDBObject("_id", 2).append("height", "480").append("width", "640"));
        images.insert(new BasicDBObject("_id", 3).append("height", "480").append("width", "640"));

        albums.insert(new BasicDBObject("_id", 67).append("images", Arrays.asList(1, 2)));
        albums.insert(new BasicDBObject("_id", 68).append("images", Arrays.asList(1)));

        Question7 question7 = new Question7(db);
        question7.cleanupImages();

        Assert.assertEquals(2, images.count());
        Assert.assertEquals(0, images.find(new BasicDBObject("_id", 3)).count());
    }

}
