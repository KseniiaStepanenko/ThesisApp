package com.thesisapp.thesisapp.Repository;

import com.thesisapp.thesisapp.DTO.DirectionFromToDTO;
import com.thesisapp.thesisapp.DTO.ShortestPathDTO;
import com.thesisapp.thesisapp.DTO.TicketDataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TicketRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void insertTicket(String ticketCombinationID, Double price, String localDepartureDateTime, String localArrivalDateTime, String redirectLink, Integer ref_flightID) {
        String sql = "INSERT IGNORE INTO ticket (ticketCombinationID, price, localDepartureDateTime, " +
                "localArrivalDateTime, redirectLink, refreshTimeStamp, ref_flightID) " +
                "VALUES ('"+ticketCombinationID+"',"+price+",'"+localDepartureDateTime+
                "','"+localArrivalDateTime+"','"+redirectLink+"',CURRENT_TIMESTAMP,"+ref_flightID+")";
        this.jdbcTemplate.update(sql);
    }

    public List<TicketDataDTO> getResultTicketDataByIdList (String ids){
        String sql = "SELECT cFrom.cityName AS cityNameFrom, cTo.cityName AS cityNameTo, countryFrom.countryName AS countryNameFrom, countryTo.countryName AS countryNameTo, aFrom.airpodeCode AS airportFrom, aTo.airpodeCode AS airportTo, f.flightNumber AS flightNumber, t.price AS price, t.localDepartureDateTime AS localDepartureDateTime, t.localArrivalDateTime AS localArrivalDateTime, t.redirectLink AS redirectLink " +
                "FROM ticket t " +
                "LEFT JOIN flight f ON t.ref_flightID = f.flightID " +
                "LEFT JOIN airport aFrom ON f.airportFrom = aFrom.airportId " +
                "LEFT JOIN airport aTo ON f.airportTo = aTo.airportId " +
                "LEFT JOIN city cFrom ON aFrom.ref_cityID = cFrom.cityID " +
                "LEFT JOIN city cTo ON aTo.ref_cityID = cTo.cityID " +
                "LEFT JOIN country countryFrom ON cFrom.ref_countryID = countryFrom.countryID " +
                "LEFT JOIN country countryTo ON cTo.ref_countryID = countryTo.countryID " +
                "WHERE t.ticketCombinationID IN (" + ids + ")" +
                "ORDER BY DATE(localDepartureDateTime);";
        return jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<TicketDataDTO>(TicketDataDTO.class));
    }

    public List<TicketDataDTO> getTicketDataBy3TicketId (String id1, String id2, String id3){
        String sql = "SELECT cFrom.cityName AS cityNameFrom, cTo.cityName AS cityNameTo, countryFrom.countryName AS countryNameFrom, countryTo.countryName AS countryNameTo, aFrom.airpodeCode AS airportFrom, aTo.airpodeCode AS airportTo, f.flightNumber AS flightNumber, t.price AS price, t.localDepartureDateTime AS localDepartureDateTime, t.localArrivalDateTime AS localArrivalDateTime, t.redirectLink AS redirectLink " +
                "FROM ticket t " +
                "LEFT JOIN flight f ON t.ref_flightID = f.flightID " +
                "LEFT JOIN airport aFrom ON f.airportFrom = aFrom.airportId " +
                "LEFT JOIN airport aTo ON f.airportTo = aTo.airportId " +
                "LEFT JOIN city cFrom ON aFrom.ref_cityID = cFrom.cityID " +
                "LEFT JOIN city cTo ON aTo.ref_cityID = cTo.cityID " +
                "LEFT JOIN country countryFrom ON cFrom.ref_countryID = countryFrom.countryID " +
                "LEFT JOIN country countryTo ON cTo.ref_countryID = countryTo.countryID " +
                "WHERE t.ticketCombinationID IN (\""+id1+ "\",\"" +id2+ "\",\"" + id3+"\")" +
                "ORDER BY DATE(localDepartureDateTime);";
        return jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<TicketDataDTO>(TicketDataDTO.class));
    }

    public List<TicketDataDTO> getTicketDataBy5TicketId (String id1, String id2, String id3, String id4, String id5){
        String sql = "SELECT cFrom.cityName AS cityNameFrom, cTo.cityName AS cityNameTo, countryFrom.countryName AS countryNameFrom, countryTo.countryName AS countryNameTo, aFrom.airpodeCode AS airportFrom, aTo.airpodeCode AS airportTo, f.flightNumber AS flightNumber, t.price AS price, t.localDepartureDateTime AS localDepartureDateTime, t.localArrivalDateTime AS localArrivalDateTime, t.redirectLink AS redirectLink " +
                "FROM ticket t " +
                "LEFT JOIN flight f ON t.ref_flightID = f.flightID " +
                "LEFT JOIN airport aFrom ON f.airportFrom = aFrom.airportId " +
                "LEFT JOIN airport aTo ON f.airportTo = aTo.airportId " +
                "LEFT JOIN city cFrom ON aFrom.ref_cityID = cFrom.cityID " +
                "LEFT JOIN city cTo ON aTo.ref_cityID = cTo.cityID " +
                "LEFT JOIN country countryFrom ON cFrom.ref_countryID = countryFrom.countryID " +
                "LEFT JOIN country countryTo ON cTo.ref_countryID = countryTo.countryID " +
                "WHERE t.ticketCombinationID IN (\""+id1+ "\",\"" +id2+ "\",\"" + id3+ "\",\"" + id4 + "\",\"" + id5 + "\")" +
                "ORDER BY DATE(localDepartureDateTime);";
        return jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<TicketDataDTO>(TicketDataDTO.class));
    }

    public List<TicketDataDTO> getTicketDataBy4TicketId (String id1, String id2, String id3, String id4){
        String sql = "SELECT cFrom.cityName AS cityNameFrom, cTo.cityName AS cityNameTo, countryFrom.countryName AS countryNameFrom, countryTo.countryName AS countryNameTo, aFrom.airpodeCode AS airportFrom, aTo.airpodeCode AS airportTo, f.flightNumber AS flightNumber, t.price AS price, t.localDepartureDateTime AS localDepartureDateTime, t.localArrivalDateTime AS localArrivalDateTime, t.redirectLink AS redirectLink " +
                "FROM ticket t " +
                "LEFT JOIN flight f ON t.ref_flightID = f.flightID " +
                "LEFT JOIN airport aFrom ON f.airportFrom = aFrom.airportId " +
                "LEFT JOIN airport aTo ON f.airportTo = aTo.airportId " +
                "LEFT JOIN city cFrom ON aFrom.ref_cityID = cFrom.cityID " +
                "LEFT JOIN city cTo ON aTo.ref_cityID = cTo.cityID " +
                "LEFT JOIN country countryFrom ON cFrom.ref_countryID = countryFrom.countryID " +
                "LEFT JOIN country countryTo ON cTo.ref_countryID = countryTo.countryID " +
                "WHERE t.ticketCombinationID IN (\""+id1+ "\",\"" +id2+ "\",\"" + id3+ "\",\"" + id4 + "\")" +
                "ORDER BY DATE(localDepartureDateTime);";
        return jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<TicketDataDTO>(TicketDataDTO.class));
    }

    public List<DirectionFromToDTO> getDirectionsByDateAndStart(String flyFrom, String start, String finish, String flightDate) {
        String sql = "SELECT aFrom.airpodeCode AS airportFromList, aTo.airpodeCode AS airportToList " +
                "FROM ticket t " +
                "LEFT JOIN flight f ON t.ref_flightID = f.flightID " +
                "LEFT JOIN airport aFrom ON f.airportFrom = aFrom.airportId " +
                "LEFT JOIN airport aTo ON f.airportTo = aTo.airportId " +
                "WHERE DATE(localDepartureDateTime) = '" + flightDate + "' " +
                "AND aFrom.airpodeCode IN (" + flyFrom + ") " +
                "AND aTo.airpodeCode NOT IN (" + start + ") "+
                "AND aTo.airpodeCode <> " + finish + ";";
        return jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<DirectionFromToDTO>(DirectionFromToDTO.class));
    }

    public List<ShortestPathDTO> getLastTicketForShortestPath(String flyFrom, String flyTo, String flightDate){
        String sql = "SELECT t.ticketCombinationID, t.price, a.airpodeCode AS airportFromList, b.airpodeCode AS airportToList " +
                "FROM ticket t " +
                "LEFT JOIN flight f ON t.ref_flightID = f.flightID " +
                "LEFT JOIN airport a ON f.airportFrom = a.airportId " +
                "LEFT JOIN airport b ON f.airportTo = b.airportId " +
                "WHERE DATE(localDepartureDateTime) = '" + flightDate + "' " +
                "AND a.airpodeCode IN (" + flyFrom + ") AND b.airpodeCode IN(" + flyTo + ");";
        return jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<ShortestPathDTO>(ShortestPathDTO.class));
    }

}
