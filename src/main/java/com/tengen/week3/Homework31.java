package com.tengen.week3;

import com.mongodb.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Write a program in the language of your choice that will remove the lowest homework score for each student.
 * Since there is a single document for each student containing an array of scores, you will need to update the scores
 * array and remove the homework.
 *
 * @author fede
 */
public class Homework31 {

    public void homework31(DBCollection collection) {

        DBCursor dbObjects = collection.find();
        Map<DBObject, DBObject> studentToMinScore = new HashMap<>();
        while (dbObjects.hasNext()) {
            DBObject currentStudent = dbObjects.next();

            BasicDBList scores = (BasicDBList) currentStudent.get("scores");
            for (Object scoreObject : scores) {
                DBObject currentScore = (DBObject) scoreObject;

                if ("homework".equals(currentScore.get("type"))) {
                    Double currentScoreValue = (Double) currentScore.get("score");
                    DBObject previousScore = studentToMinScore.get(currentStudent);

                    if (previousScore == null) {
                        studentToMinScore.put(currentStudent, currentScore);
                    } else {
                        Double previous = (Double) previousScore.get("score");
                        if (currentScoreValue.compareTo(previous) < 0) {
                            studentToMinScore.put(currentStudent, currentScore);
                        }
                    }
                }
            }


        }
        for (Map.Entry<DBObject, DBObject> entry : studentToMinScore.entrySet()) {
            DBObject student = entry.getKey();
            BasicDBList scores = (BasicDBList) student.get("scores");
            int index = scores.indexOf(entry.getValue());
            scores.remove(index);
            student.put("scores", scores);
            collection.update(QueryBuilder.start("_id").is(student.get("_id")).get(), student);
        }
    }

    public static void main(String[] args) throws Exception {
        MongoClient client = new MongoClient();
        DB db = client.getDB("school");
        DBCollection collection = db.getCollection("students");
        new Homework31().homework31(collection);
    }
}
