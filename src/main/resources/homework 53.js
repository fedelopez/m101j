/*
 Who's the easiest grader on campus?
 {
 "_id" : ObjectId("50b59cd75bed76f46522c392"),
 "student_id" : 10,
 "class_id" : 5,
 "scores" : [
 {
 "type" : "exam",
 "score" : 69.17634380939022
 },
 {
 "type" : "quiz",
 "score" : 61.20182926719762
 },
 {
 "type" : "homework",
 "score" : 73.3293624199466
 },
 {
 "type" : "homework",
 "score" : 15.206314042622903
 },
 {
 "type" : "homework",
 "score" : 36.75297723087603
 },
 {
 "type" : "homework",
 "score" : 64.42913107330241
 }
 ]
 }
 Your task is to calculate the class with the best average student performance. This involves calculating an average
 for each student in each class of all non-quiz assessments and then averaging those numbers to get a class average.
 To be clear, each student's average includes only exams and homework grades. Don't include their quiz scores in the calculation.

 What is the class_id which has the highest average student perfomance?
 */
db.grades.aggregate([
    {"$unwind": "$scores"},
    {"$match": {"scores.type": { $ne: "quiz" }}},
    {"$project": {_id: 1, "student_id": 1, "class_id": 1, "score": "$scores.score"}},
    {"$group": {"_id": {"student_id": "$student_id", "class_id": "$class_id"}, "student_average": {"$avg": "$score"}}},
    {"$group": {"_id": "$_id.class_id", "average": {"$avg": "$student_average"}}},
    {"$sort": {average: -1}}
]);