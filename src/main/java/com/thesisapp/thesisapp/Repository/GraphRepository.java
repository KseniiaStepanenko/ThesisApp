package com.thesisapp.thesisapp.Repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GraphRepository extends Neo4jRepository {
    @Query("MERGE (d0:RoadGraph {name:{0},departure: date({1})})")
    void createNode(String start, String date);

    @Query("MERGE (c0:RoadGraph {name: {0}, departure: date({2})}) " +
            "MERGE (c1:RoadGraph {name: {1}, departure: date({3})}) " +
            "MERGE (c1)<-[:ROAD {ticketId: {4}, cost: {5}}]-(c0) ")
    void createGraph(String start, String finish, String startDate, String finishDate, String id, Double price);

    @Query("WI{0}")
    void createGraphNew(String query);

}



//   WITH [{name: "Event 1", timetree: {day: 1, month: 1, year: 2014}},
//        {name: "Event 2", timetree: {day: 2, month: 1, year: 2014}}] AS events
//        FOREACH (event IN events |
//        CREATE (e:Event {name: event.name})
//        MATCH (year:Year {year: event.timetree.year }),
//        (year)-[:HAS_MONTH]->(month {month: event.timetree.month }),
//        (month)-[:HAS_DAY]->(day {day: event.timetree.day })
//        CREATE (e)-[:HAPPENED_ON]->(day))