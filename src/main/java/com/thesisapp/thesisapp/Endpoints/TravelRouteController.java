package com.thesisapp.thesisapp.Endpoints;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.gson.Gson;
import com.thesisapp.thesisapp.DTO.DirectionFromToDTO;
import com.thesisapp.thesisapp.DTO.ShortestPathDTO;
import com.thesisapp.thesisapp.DTO.TicketDataDTO;
import com.thesisapp.thesisapp.Entity.Route;
import com.thesisapp.thesisapp.Repository.GraphRepository;
import com.thesisapp.thesisapp.Repository.TicketRepository;
import com.thesisapp.thesisapp.Repository.TravelRouteSolutionRepository;
import org.neo4j.driver.v1.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Api(name = "travelroute", version = "v1")
public class TravelRouteController {

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    GraphRepository graphRepository;

    @Autowired
    TravelRouteSolutionRepository travelRouteSolutionRepository;

    @ApiMethod(name = "2city",  path = "travelroute/2city/{flyFrom}/{flyTo}/{startDate}/{finishDate}")
    public List<TicketDataDTO> get2City(@Named("flyFrom") String start, @Named("flyTo") String finish, @Named("startDate") String startDate, @Named("finishDate") String finishDate) {
        String middleDate1 = incrementDatesAccordingToTripDuration(startDate, finishDate, findTripDurationByGivingDates(startDate, finishDate) / 2);
        List<DirectionFromToDTO> temp1 = ticketRepository.getDirectionsByDateAndStart("'"+ start +"'","'"+ start +"'","'"+ finish+"'", startDate);
        List<DirectionFromToDTO> temp2 = ticketRepository.getDirectionsByDateAndStart(makeAirportCodeStringList(makeAirportCodeToDistinctStringArrayDirection(temp1)),"'"+ start +"'", "'" + finish + "'", middleDate1);

        List<ShortestPathDTO> tickets3 = ticketRepository.getLastTicketForShortestPath(makeAirportCodeStringList(makeAirportCodeToDistinctStringArrayDirection(temp2)), "'" + finish + "'", finishDate);
        List<ShortestPathDTO> tickets1 = ticketRepository.getLastTicketForShortestPath("'"+ start +"'", makeAirportCodeStringList(makeAirportCodeFromDistinctStringArrayDirection(temp2)), startDate);
        List<ShortestPathDTO> tickets2 = ticketRepository.getLastTicketForShortestPath(makeAirportCodeStringList(makeAirportCodeToDistinctStringArray(tickets1)), makeAirportCodeStringList(makeAirportCodeFromDistinctStringArray(tickets3)), middleDate1);

        try (Session session = GraphDatabase.driver( "bolt://100.26.154.131:37841", AuthTokens.basic( "neo4j", "staple-runners-vector")).session() )
        {
            session.writeTransaction( new TransactionWork<String>()
            {
                @Override
                public String execute( Transaction tx )
                {
                    tx.run(createGraph(tickets1, startDate, middleDate1, 2));
                    tx.run(createGraph(tickets2, middleDate1, finishDate, 2));
                    tx.run(createGraph(tickets3, finishDate, finishDate, 2));
                    return "";
                }
            } );
        }
        Iterable<Route> result = travelRouteSolutionRepository.find2CitySolution(start, finish, startDate, finishDate);

        List<TicketDataDTO> resultTickets = ticketRepository.getTicketDataBy3TicketId((String) ((LinkedHashMap) ((ArrayList) result).get(0)).get("ticketID_1"), (String) ((LinkedHashMap) ((ArrayList) result).get(0)).get("ticketID_2"), (String) ((LinkedHashMap) ((ArrayList) result).get(0)).get("ticketID_3"));
        return resultTickets;
    }

    @ApiMethod(name = "3city",  path = "travelroute/3city/{flyFrom}/{flyTo}/{startDate}/{finishDate}")
    public List<TicketDataDTO> get3City(@Named("flyFrom") String start, @Named("flyTo") String finish, @Named("startDate") String startDate, @Named("finishDate") String finishDate) {
        String middleDate1 = incrementDatesAccordingToTripDuration(startDate, finishDate, findTripDurationByGivingDates(startDate, finishDate) / 3);
        String middleDate2 = incrementDatesAccordingToTripDuration(middleDate1, finishDate, findTripDurationByGivingDates(middleDate1, finishDate) / 2);

        List<DirectionFromToDTO> temp1 = ticketRepository.getDirectionsByDateAndStart("'"+ start +"'","'"+ start +"'", "'"+ finish+"'", startDate);
        List<DirectionFromToDTO> temp2 = ticketRepository.getDirectionsByDateAndStart(makeAirportCodeStringList(makeAirportCodeToDistinctStringArrayDirection(temp1)), "'"+ start +"'", "'" + finish + "'", middleDate1);
        List<DirectionFromToDTO> temp3 = ticketRepository.getDirectionsByDateAndStart(makeAirportCodeStringList(makeAirportCodeToDistinctStringArrayDirection(temp2)), "'"+ start +"'", "'" + finish + "'", middleDate2);

        List<ShortestPathDTO> tickets4 = ticketRepository.getLastTicketForShortestPath(makeAirportCodeStringList(makeAirportCodeToDistinctStringArrayDirection(temp3)), "'" + finish + "'", finishDate);
        List<ShortestPathDTO> tickets3 = ticketRepository.getLastTicketForShortestPath(makeAirportCodeStringList(makeAirportCodeToDistinctStringArrayDirection(temp2)), makeAirportCodeStringList(makeAirportCodeFromDistinctStringArray(tickets4)), middleDate2);
        List<ShortestPathDTO> tickets2 = ticketRepository.getLastTicketForShortestPath(makeAirportCodeStringList(makeAirportCodeToDistinctStringArrayDirection(temp1)), makeAirportCodeStringList(makeAirportCodeFromDistinctStringArray(tickets3)), middleDate1);
        List<ShortestPathDTO> tickets1 = ticketRepository.getLastTicketForShortestPath("'"+ start +"'", makeAirportCodeStringList(makeAirportCodeFromDistinctStringArray(tickets2)), startDate);

        try ( Session session =  GraphDatabase.driver( "bolt://100.26.154.131:37841", AuthTokens.basic( "neo4j", "staple-runners-vector")).session() ) {
            session.writeTransaction(new TransactionWork<String>() {
                @Override
                public String execute(Transaction tx) {
                    tx.run(createGraph(tickets1, startDate, middleDate1, 3));
                    tx.run(createGraph(tickets2, middleDate1, middleDate2, 3));
                    tx.run(createGraph(tickets3, middleDate2, finishDate, 3));
                    tx.run(createGraph(tickets4, finishDate, finishDate, 3));
                    return "";
                }
            });
        }
        Iterable<Route> resultRoute = travelRouteSolutionRepository.find3CitySolution(start, finish, startDate, finishDate);
        List<TicketDataDTO> resultTickets = ticketRepository.getTicketDataBy4TicketId((String) ((LinkedHashMap) ((ArrayList) resultRoute).get(0)).get("ticketID_1"), (String) ((LinkedHashMap) ((ArrayList) resultRoute).get(0)).get("ticketID_2"), (String) ((LinkedHashMap) ((ArrayList) resultRoute).get(0)).get("ticketID_3"), (String) ((LinkedHashMap) ((ArrayList) resultRoute).get(0)).get("ticketID_4"));
        return resultTickets;
    }

    @ApiMethod(name = "4city",  path = "travelroute/4city/{flyFrom}/{flyTo}/{startDate}/{finishDate}")
    public List<TicketDataDTO>  get4City(@Named("flyFrom") String start, @Named("flyTo") String finish, @Named("startDate") String startDate, @Named("finishDate") String finishDate) {
        String middleDate1 = incrementDatesAccordingToTripDuration(startDate, finishDate, findTripDurationByGivingDates(startDate, finishDate) / 4);
        String middleDate2 = incrementDatesAccordingToTripDuration(middleDate1, finishDate, findTripDurationByGivingDates(middleDate1, finishDate) / 3);
        String middleDate3 = incrementDatesAccordingToTripDuration(middleDate2, finishDate, findTripDurationByGivingDates(middleDate2, finishDate) / 2);

        List<DirectionFromToDTO> temp1 = ticketRepository.getDirectionsByDateAndStart("'"+ start +"'", "'"+ start +"'", "'"+ finish+"'", startDate);
        List<DirectionFromToDTO> temp2 = ticketRepository.getDirectionsByDateAndStart(makeAirportCodeStringList(makeAirportCodeToDistinctStringArrayDirection(temp1)), "'"+ start +"'", "'" + finish + "'", middleDate1);
        List<DirectionFromToDTO> temp3 = ticketRepository.getDirectionsByDateAndStart(makeAirportCodeStringList(makeAirportCodeToDistinctStringArrayDirection(temp2)), "'"+ start +"'", "'" + finish + "'", middleDate2);
        List<DirectionFromToDTO> temp4 = ticketRepository.getDirectionsByDateAndStart(makeAirportCodeStringList(makeAirportCodeToDistinctStringArrayDirection(temp3)), "'"+ start +"'", "'" + finish + "'", middleDate3);

        List<ShortestPathDTO> tickets5 = ticketRepository.getLastTicketForShortestPath(makeAirportCodeStringList(makeAirportCodeToDistinctStringArrayDirection(temp4)), "'" + finish + "'", finishDate);
        List<ShortestPathDTO> tickets4 = ticketRepository.getLastTicketForShortestPath(makeAirportCodeStringList(makeAirportCodeToDistinctStringArrayDirection(temp3)), makeAirportCodeStringList(makeAirportCodeFromDistinctStringArray(tickets5)), middleDate3);
        List<ShortestPathDTO> tickets3 = ticketRepository.getLastTicketForShortestPath(makeAirportCodeStringList(makeAirportCodeToDistinctStringArrayDirection(temp2)), makeAirportCodeStringList(makeAirportCodeFromDistinctStringArray(tickets4)), middleDate2);
        List<ShortestPathDTO> tickets2 = ticketRepository.getLastTicketForShortestPath(makeAirportCodeStringList(makeAirportCodeToDistinctStringArrayDirection(temp1)), makeAirportCodeStringList(makeAirportCodeFromDistinctStringArray(tickets3)), middleDate1);
        List<ShortestPathDTO> tickets1 = ticketRepository.getLastTicketForShortestPath("'"+ start +"'", makeAirportCodeStringList(makeAirportCodeFromDistinctStringArray(tickets2)), startDate);

        try ( Session session =  GraphDatabase.driver( "bolt://100.26.154.131:37841", AuthTokens.basic( "neo4j", "staple-runners-vector")).session() ) {
            session.writeTransaction(new TransactionWork<String>() {
                @Override
                public String execute(Transaction tx) {
                    tx.run(createGraph(tickets1, startDate, middleDate1, 4));
                    tx.run(createGraph(tickets2, middleDate1, middleDate2, 4));
                    tx.run(createGraph(tickets3, middleDate2, middleDate3, 4));
                    tx.run(createGraph(tickets4, middleDate3, finishDate, 4));
                    tx.run(createGraph(tickets5, finishDate, finishDate, 4));
                    return "";
                }
            });
        }
        Iterable<Route> resultRoute = travelRouteSolutionRepository.find4CitySolution(start, finish, startDate, finishDate);
        List<TicketDataDTO> resultTickets = ticketRepository.getTicketDataBy5TicketId((String) ((LinkedHashMap) ((ArrayList) resultRoute).get(0)).get("ticketID_1"), (String) ((LinkedHashMap) ((ArrayList) resultRoute).get(0)).get("ticketID_2"), (String) ((LinkedHashMap) ((ArrayList) resultRoute).get(0)).get("ticketID_3"), (String) ((LinkedHashMap) ((ArrayList) resultRoute).get(0)).get("ticketID_4"), (String) ((LinkedHashMap) ((ArrayList) resultRoute).get(0)).get("ticketID_5"));
        return resultTickets;
    }

    private String replaceFromJson (List<ShortestPathDTO> tickets) {
        return new Gson().toJson(tickets).replace(",\"",",").replace("\":",":").replace("{\"","{");
    }

    private String createGraph(List<ShortestPathDTO> tickets, String date1, String date2, Integer num){
        return "WITH "+replaceFromJson(tickets)+" AS data " +
                "FOREACH (eachData IN data | " +
                "MERGE (c0:RoadGraph"+num+" {name: eachData.airportFromList, departure: date('"+date1+"')}) " +
                "MERGE (c1:RoadGraph"+num+" {name: eachData.airportToList, departure: date('"+date2+"')}) " +
                "MERGE (c1)<-[:ROAD {ticketId: eachData.ticketCombinationID, cost: eachData.price}]-(c0)) ";
    }

    private String[] makeAirportCodeToDistinctStringArrayDirection(List<DirectionFromToDTO> tickets1) {
        String[] array = new String[tickets1.size()];
        for (int i = 0; i<tickets1.size(); i++){
            array[i]= tickets1.get(i).getAirportToList();
        }
        String[] unique = Arrays.stream(array).distinct().toArray(String[]::new);
        return unique;
    }

    private String[] makeAirportCodeFromDistinctStringArrayDirection(List<DirectionFromToDTO> tickets1) {
        String[] array = new String[tickets1.size()];
        for (int i = 0; i<tickets1.size(); i++){
            array[i]= tickets1.get(i).getAirportFromList();
        }
        String[] unique = Arrays.stream(array).distinct().toArray(String[]::new);
        return unique;
    }

    private String makeAirportCodeStringList(String[] tickets1) {
        String t = "";
        for (int i = 0; i<tickets1.length; i++){
            t+= "'"+ tickets1[i]+"',";
        }
        t = t.substring(0,t.length()-1);
        return t;
    }

    private String[] makeAirportCodeToDistinctStringArray(List<ShortestPathDTO> tickets1) {
        String[] array = new String[tickets1.size()];
        for (int i = 0; i<tickets1.size(); i++){
            array[i]= tickets1.get(i).getAirportToList();
        }
        String[] unique = Arrays.stream(array).distinct().toArray(String[]::new);
        return unique;
    }

    private String[] makeAirportCodeFromDistinctStringArray(List<ShortestPathDTO> tickets1) {
        String[] array = new String[tickets1.size()];
        for (int i = 0; i<tickets1.size(); i++){
            array[i]= tickets1.get(i).getAirportFromList();
        }
        String[] rs = Arrays.stream(array).distinct().toArray(String[]::new);
        return rs;
    }

    private int findTripDurationByGivingDates(String startDate, String endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = null;
        Date date2 = null;
        try {
            date1=sdf.parse(startDate);
            date2=sdf.parse(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int tripDuration = getNumDaysBetweenDates(date1, date2);

        return tripDuration;
    }

    private int getNumDaysBetweenDates(Date d1, Date d2) {
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

    private String incrementDatesAccordingToTripDuration(String startDate, String endDate, int incrementalValue) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        Date date1 = null;
        try {
            date1=sdf.parse(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date1);

        c.add(Calendar.DAY_OF_MONTH, incrementalValue);
        String newDate = sdf.format(c.getTime());
        return newDate;
    }

}