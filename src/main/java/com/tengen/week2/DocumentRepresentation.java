package com.tengen.week2;

import com.mongodb.BasicDBObject;

import java.util.Arrays;

/**
 * @author fede
 */
public class DocumentRepresentation {

    public static void main(String[] args) {
        BasicDBObject object = new BasicDBObject();
        object.put("userName", "John");
        object.put("languages", Arrays.asList("Java", "C++"));
        object.put("address", new BasicDBObject("street", "20 Main").append("town", "Sydney"));
    }

}

