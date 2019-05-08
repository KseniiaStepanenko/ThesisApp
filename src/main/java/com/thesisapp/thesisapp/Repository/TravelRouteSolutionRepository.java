package com.thesisapp.thesisapp.Repository;

import com.thesisapp.thesisapp.Entity.Route;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelRouteSolutionRepository extends Neo4jRepository {

    @Query("MATCH (start:RoadGraph2{name:{0}, departure: date({2})}), (end:RoadGraph2{name:{1}, departure: date({3})}) " +
            "CALL algo.shortestPath.stream(start, end, \"cost\") " +
            "YIELD nodeId " +
            "WITH COLLECT(nodeId) as nodeIdList, start, end " +
            "MATCH (a1:RoadGraph2)-[r1:ROAD]->(a2:RoadGraph2)-[r2:ROAD]->(a3:RoadGraph2)-[r3:ROAD]->(a4:RoadGraph2) " +
            "WHERE a1 = start " +
            "AND id(a2) IN nodeIdList " +
            "AND id(a3) IN nodeIdList " +
            "AND a4 = end " +
            "RETURN r1.ticketId AS ticketID_1, r2.ticketId AS ticketID_2, r3.ticketId AS ticketID_3")
    Iterable<Route> find2CitySolution(String origin, String destination, String startDate, String endDate);

    @Query("MATCH (start:RoadGraph3{name:{0}, departure: date({2})}), (end:RoadGraph3{name:{1}, departure: date({3})}) " +
            "CALL algo.shortestPath.stream(start, end, \"cost\") " +
            "YIELD nodeId " +
            "WITH COLLECT(nodeId) as nodeIdList, start, end " +
            "MATCH (a1:RoadGraph3)-[r1:ROAD]->(a2:RoadGraph3)-[r2:ROAD]->(a3:RoadGraph3)-[r3:ROAD]->(a4:RoadGraph3)-[r4:ROAD]->(a5:RoadGraph3) " +
            "WHERE a1 = start " +
            "AND id(a2) IN nodeIdList " +
            "AND id(a3) IN nodeIdList " +
            "AND id(a4) IN nodeIdList " +
            "AND a5 = end " +
            "RETURN r1.ticketId AS ticketID_1, r2.ticketId AS ticketID_2, r3.ticketId AS ticketID_3, r4.ticketId  AS ticketID_4")
    Iterable<Route> find3CitySolution(String origin, String destination, String startDate, String endDate);

    @Query("MATCH (start:RoadGraph4{name:{0}, departure: date({2})}), (end:RoadGraph4{name:{1}, departure: date({3})}) " +
            "CALL algo.shortestPath.stream(start, end, \"cost\") " +
            "YIELD nodeId " +
            "WITH COLLECT(nodeId) as nodeIdList, start, end " +
            "MATCH (a1:RoadGraph4)-[r1:ROAD]->(a2:RoadGraph4)-[r2:ROAD]->(a3:RoadGraph4)-[r3:ROAD]->(a4:RoadGraph4)-[r4:ROAD]->(a5:RoadGraph4)-[r5:ROAD]->(a6:RoadGraph4) " +
            "WHERE a1 = start " +
            "AND id(a2) IN nodeIdList " +
            "AND id(a3) IN nodeIdList " +
            "AND id(a4) IN nodeIdList " +
            "AND id(a5) IN nodeIdList " +
            "AND a6 = end " +
            "RETURN r1.ticketId AS ticketID_1, r2.ticketId AS ticketID_2, r3.ticketId AS ticketID_3, r4.ticketId  AS ticketID_4, r5.ticketId  AS ticketID_5 ")
    Iterable<Route> find4CitySolution(String origin, String destination, String startDate, String endDate);

    @Query("MATCH (n:RoadGraph2) DETACH DELETE n")
    void delete2CityGraph();

    @Query("MATCH (n:RoadGraph3) DETACH DELETE n")
    void delete3CityGraph();

    @Query("MATCH (n:RoadGraph4) DETACH DELETE n")
    void delete4CityGraph();
}
