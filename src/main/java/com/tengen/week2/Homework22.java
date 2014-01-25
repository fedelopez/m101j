package com.tengen.week2;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fede
 */
public class Homework22 {

    //Now it’s your turn to analyze the data set.
    //Find all exam scores greater than or equal to 65. and sort those scores from lowest to highest.
    //What is the student_id of the lowest exam score above 65?
    public void homework22(DBCollection collection) {
        DBCursor dbObjects = collection.find(new BasicDBObject("type", "homework"));
        Map<Integer, DBObject> studentToMinScore = new HashMap<>();
        while (dbObjects.hasNext()) {
            DBObject currentStudent = dbObjects.next();
            Integer currentStudentId = (Integer) currentStudent.get("student_id");
            Double currentScore = (Double) currentStudent.get("score");

            DBObject student = studentToMinScore.get(currentStudentId);
            if (student == null) {
                studentToMinScore.put(currentStudentId, currentStudent);
            } else {
                Double previousScore = (Double) student.get("score");
                if (currentScore.compareTo(previousScore) < 0) {
                    studentToMinScore.put(currentStudentId, currentStudent);
                }
            }
        }
        for (Map.Entry<Integer, DBObject> entry : studentToMinScore.entrySet()) {
            collection.remove(entry.getValue());
        }
    }

}
