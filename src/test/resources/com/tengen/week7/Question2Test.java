package com.tengen.week7;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.UnknownHostException;

/**
 * @author fede
 */
public class Question2Test {

    private DBCollection collection;

    @Before
    public void setup() throws UnknownHostException {
        MongoClient client = new MongoClient();

        DB db = client.getDB("exam");
        collection = db.getCollection("messages");
    }

    @Test
    public void numberOfEmailsOption1() {
        Question2 q1 = new Question2(collection);
        int actual = q1.numberOfEmails("susan.mara@enron.com", "jeff.dasovich@enron.com");
        Assert.assertEquals(750, actual);
    }


    @Test
    public void numberOfEmailsOption2() {
        Question2 q1 = new Question2(collection);
        int actual = q1.numberOfEmails("susan.mara@enron.com", "richard.shapiro@enron.com");
        Assert.assertEquals(616, actual);
    }

    @Test
    public void numberOfEmailsOption3() {
        Question2 q1 = new Question2(collection);
        int actual = q1.numberOfEmails("soblander@carrfut.com", "soblander@carrfut.com");
        Assert.assertEquals(679, actual);
    }

    @Test
    public void numberOfEmailsOption4() {
        Question2 q1 = new Question2(collection);
        int actual = q1.numberOfEmails("susan.mara@enron.com", "james.steffes@enron.com");
        Assert.assertEquals(646, actual);
    }

    @Test
    public void numberOfEmailsOption5() {
        Question2 q1 = new Question2(collection);
        int actual = q1.numberOfEmails("evelyn.metoyer@enron.com", "kate.symes@enron.com");
        Assert.assertEquals(567, actual);
    }
}
