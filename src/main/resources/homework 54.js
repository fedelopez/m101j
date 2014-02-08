/*
 In this problem you will calculate the number of people who live in a zip code in the US where the city starts with a digit.
 We will take that to mean they don't really live in a city.

 Using the aggregation framework, calculate the sum total of people who are living in a zip code where the city starts with a digit.

 */
db.zips.aggregate([
    {$project: { _id: 1, pop: 1, first_char: {$substr: ["$city", 0, 1]} } },
    {"$match": {"first_char": {$regex: /[0-9]/i}}},
    {"$group": {"_id": null, "total": {"$sum": "$pop"}}}
]);