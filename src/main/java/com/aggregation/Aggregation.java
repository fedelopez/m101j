package com.aggregation;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author fede
 */
public class Aggregation {

    private static final String SITE = "Acme Labs";
    private static final String PROJECT_A = "Roadrunner";

    private final DB db;
    private final String collectionName;
    private final int total;
    private final int repeated;

    public Aggregation(String databaseName, String collectionName, int total, int repeated) throws UnknownHostException {
        this.collectionName = collectionName;
        this.total = total;
        this.repeated = repeated;
        MongoClient client = new MongoClient();
        db = client.getDB(databaseName);
    }

    public void populateCollection() {
        DBCollection collection = db.getCollection(collectionName);
        collection.drop();
        System.out.println("About to insert " + total + " total...");
        insert(collection, total);
        System.out.println("About to insert " + repeated + " repeated...");
        insert(collection, repeated);
    }

    private void insert(DBCollection collection, int count) {
        List<DBObject> interpretations = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            interpretations.add(new BasicDBObject("date", new Date()).append("site", SITE).append("casename", "case-" + i).append("project", PROJECT_A));
        }
        collection.insert(interpretations);
        System.out.println("Total " + collectionName + " after insert = " + collection.count());
    }

    /**
     * //db.interpretations.aggregate([
     * //    {$match: {project: "Roadrunner"}},
     * //    {$project: {_id: 0, casename: 1}},
     * //    {"$group": {"_id": "$casename"}},
     * //    {"$group": {"_id": null, "total": {"$sum": 1}}}
     * //]);
     */
    public int distinctCaseNames() {
        DBCollection collection = db.getCollection(collectionName);

        QueryBuilder builder = QueryBuilder.start("site").is(SITE).and("project").is(PROJECT_A);
        DBObject match = new BasicDBObject("$match", builder.get());

        DBObject project = new BasicDBObject("$project", new BasicDBObject("_id", 0).append("casename", 1));
        DBObject groupByCaseName = new BasicDBObject("$group", new BasicDBObject("_id", "$casename"));
        DBObject groupToCountCaseNames = new BasicDBObject("$group", new BasicDBObject("_id", "null").append("total", new BasicDBObject("$sum", 1)));

        AggregationOutput aggregate = collection.aggregate(match, project, groupByCaseName, groupToCountCaseNames);
        String res = aggregate.toString();
        System.out.println("res = " + res);

        DBObject next = aggregate.results().iterator().next();
        return (int) next.get("total");
    }
}
