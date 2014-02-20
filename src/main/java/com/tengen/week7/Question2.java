package com.tengen.week7;

import com.mongodb.*;

import java.util.Arrays;

/**
 * @author fede
 */
public class Question2 {

    private DBCollection collection;

    public Question2(DBCollection collection) {
        this.collection = collection;
    }

    public int numberOfEmails(String from, String to) {
        QueryBuilder builder = QueryBuilder.start("headers.From").is(from).and("headers.To").in(Arrays.asList(to));
        DBCursor cursor = collection.find(builder.get(), new BasicDBObject("headers.From", 1).append("headers.To", 1).append("_id", 1));

        int count = 0;
        while (cursor.hasNext()) {
            DBObject next = cursor.next();
            BasicDBObject headers = (BasicDBObject) next.get("headers");
            BasicDBList toRecipients = (BasicDBList) headers.get("To");
            if (toRecipients != null) {
                for (Object recipient : toRecipients) {
                    if (recipient != null && to.equals(recipient.toString())) {
                        count++;
                        break;
                    }
                }
            }
        }

        return count;
    }
}
