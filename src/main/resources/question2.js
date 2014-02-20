db.messages.aggregate(
    { $match: {"headers.From": "susan.mara@enron.com"}},
    { $unwind: "$headers.To" },
    { $group: { _id: "$_id", to: { $addToSet: "$headers.To" }}},
    { $unwind: "$to" },
    { $match: {to: "alan.comnes@enron.com"}},
    { $group: { _id: "$to", to: { $sum: 1 }}});
