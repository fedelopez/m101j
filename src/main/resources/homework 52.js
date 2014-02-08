/*
 Please calculate the average population of cities in California (abbreviation CA) and New York (NY) (taken together) with populations over 25,000.
 {
 "_id" : "92278",
 "city" : "TWENTYNINE PALMS",
 "state" : "CA",
 "pop" : 11412,
 "loc" : [
 -116.06041,
 34.237969
 ]
 }
 */
db.zips.aggregate([
    { $match: { $and: [
        { pop: { $gt: 25000}},
        { state: { $in: ["CA", "NY"] } }
    ]}
    },
    {"$group": {"_id": "$city", "total": {"$sum": "$pop"}}},
    { $project: { _id: 0, city: "$_id", total: 1 }},
    {"$group": {"_id": "$city", "average": {"$avg": "$total"}}}

]);