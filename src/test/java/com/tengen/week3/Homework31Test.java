package com.tengen.week3;

import com.google.common.io.Files;
import com.mongodb.*;
import com.mongodb.util.JSON;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.Charset.defaultCharset;

/**
 * @author fede
 */
public class Homework31Test {

    private DBCollection collection;
    private Homework31 homework31;

    @Before
    public void setup() throws Exception {
        MongoClient client = new MongoClient();
        DB db = client.getDB("test");
        collection = db.getCollection("grades");
        collection.drop();
        homework31 = new Homework31();
    }

    private void insertLines(String file) throws IOException {
        List<String> students = Files.readLines(new File(file), defaultCharset());
        for (String line : students) {
            DBObject parse = (DBObject) JSON.parse(line);
            collection.insert(parse);
        }
    }

    private void insertLines(List<String> students) throws IOException {
        for (String line : students) {
            DBObject parse = (DBObject) JSON.parse(line);
            collection.insert(parse);
        }
    }

    @Test
    public void homework31() throws IOException {
        List<String> students = new ArrayList<>();
        students.add("{ '_id' : 1, 'name' : 'Aurelia Menendez', 'scores' : [ { 'type' : 'exam', 'score' : 60.06045071030959 }, { 'type' : 'quiz', 'score' : 52.79790691903873 }, { 'type' : 'homework', 'score' : 71.76133439165544 }, { 'type' : 'homework', 'score' : 34.85718117893772 } ] }");
        students.add("{ '_id' : 2, 'name' : 'Corliss Zuk', 'scores' : [ { 'type' : 'exam', 'score' : 67.03077096065002 }, { 'type' : 'quiz', 'score' : 6.301851677835235 }, { 'type' : 'homework', 'score' : 20.18160621941858 }, { 'type' : 'homework', 'score' : 66.28344683278382 } ] }");
        students.add("{ '_id' : 3, 'name' : 'Bao Ziglar', 'scores' : [ { 'type' : 'exam', 'score' : 71.64343899778332 }, { 'type' : 'quiz', 'score' : 24.80221293650313 }, { 'type' : 'homework', 'score' : 1.694720653897219 }, { 'type' : 'homework', 'score' : 42.26147058804812 } ] }");
        students.add("{ '_id' : 4, 'name' : 'Zachary Langlais', 'scores' : [ { 'type' : 'exam', 'score' : 78.68385091304332 }, { 'type' : 'quiz', 'score' : 90.29631013680419 }, { 'type' : 'homework', 'score' : 34.41620148042529 }, { 'type' : 'homework', 'score' : 19.21886443577987 } ] }");

        insertLines(students);
        homework31.homework31(collection);

        Assert.assertEquals(4, collection.count());

        assertHomework(1, 71.76133439165544);
        assertHomework(2, 66.28344683278382);
        assertHomework(3, 42.26147058804812);
        assertHomework(4, 34.41620148042529);
    }

    private void assertHomework(int studentId, double expectedScore) {
        DBObject actual = collection.findOne(new BasicDBObject("_id", studentId));
        BasicDBList scores = (BasicDBList) actual.get("scores");
        int homeworkCount = 0;
        Double actualScore = Double.MIN_VALUE;
        for (Object objectScore : scores) {
            BasicDBObject score = (BasicDBObject) objectScore;
            System.out.println("score = " + score);
            if ("homework".equals(score.get("type"))) {
                homeworkCount++;
                actualScore = (Double) score.get("score");
            }
        }
        Assert.assertEquals(1, homeworkCount);
        Assert.assertEquals(Double.valueOf(expectedScore), actualScore);
    }
}
