package com.tengen.week2;

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
public class Homework22Test {

    private DBCollection collection;

    @Before
    public void setup() throws Exception {
        MongoClient client = new MongoClient();
        DB db = client.getDB("students");
        collection = db.getCollection("grades");
        collection.drop();

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
    public void homework22() throws IOException {
        String resource = Homework22.class.getResource("grades.22.txt").getFile();
        insertLines(resource);
        Homework22 homework22 = new Homework22();
        homework22.homework22(collection);
        DBCursor dbObjects = collection.find();
        while (dbObjects.hasNext()) {
            System.out.println("dbObjects = " + dbObjects.next());
        }
        Assert.assertEquals(600, collection.count());
        DBCursor actual = collection.find(new BasicDBObject("student_id", 100).append("type", "homework"));
        Assert.assertEquals(88.50425479139126, actual.next().get("score"));
        Assert.assertFalse(actual.hasNext());
    }

    @Test
    public void homework22_1() throws IOException {
        List<String> students = new ArrayList<>();
        students.add("{'_id':{'$oid':'50906d7fa3c412bb040eb707'},'student_id':100,'type':'exam','score':52.47081453478176}");
        students.add("{'_id':{'$oid':'50906d7fa3c412bb040eb708'},'student_id':100,'type':'quiz','score':87.26785471145698}");
        students.add("{'_id':{'$oid':'50906d7fa3c412bb040eb709'},'student_id':100,'type':'homework','score':88.50425479139126}");
        students.add("{'_id':{'$oid':'50906d7fa3c412bb040eb70a'},'student_id':100,'type':'homework','score':65.29214756759019}");

        students.add("{'_id':{'$oid':'50906d7fa3c412bb040eb70b'},'student_id':101,'type':'exam','score':23.76206244677811}");
        students.add("{'_id':{'$oid':'50906d7fa3c412bb040eb70c'},'student_id':101,'type':'quiz','score':24.19922685511702}");
        students.add("{'_id':{'$oid':'50906d7fa3c412bb040eb70d'},'student_id':101,'type':'homework','score':22.68287094862411}");
        students.add("{'_id':{'$oid':'50906d7fa3c412bb040eb70e'},'student_id':101,'type':'homework','score':80.40798767141118}");
        insertLines(students);

        Homework22 homework22 = new Homework22();
        homework22.homework22(collection);
        DBCursor dbObjects = collection.find();
        while (dbObjects.hasNext()) {
            System.out.println("dbObjects = " + dbObjects.next());
        }
    }

}
