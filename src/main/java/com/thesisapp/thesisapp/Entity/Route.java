package com.thesisapp.thesisapp.Entity;

import org.springframework.data.neo4j.annotation.QueryResult;

@QueryResult
public class Route {String ticketID_1;
    String ticketID_2;
    String ticketID_3;
    String ticketID_4;
    String ticketID_5;

    public Route(){}

    public Route(String ticketID_1, String ticketID_2, String ticketID_3) {
        this.ticketID_1 = ticketID_1;
        this.ticketID_2 = ticketID_2;
        this.ticketID_3 = ticketID_3;
    }

    public Route(String ticketID_1, String ticketID_2, String ticketID_3, String ticketID_4) {
        this.ticketID_1 = ticketID_1;
        this.ticketID_2 = ticketID_2;
        this.ticketID_3 = ticketID_3;
        this.ticketID_4 = ticketID_4;
    }

    public Route(String ticketID_1, String ticketID_2, String ticketID_3, String ticketID_4, String ticketID_5) {
        this.ticketID_1 = ticketID_1;
        this.ticketID_2 = ticketID_2;
        this.ticketID_3 = ticketID_3;
        this.ticketID_4 = ticketID_4;
        this.ticketID_5 = ticketID_5;
    }

    public String getTicketID_1() {
        return ticketID_1;
    }

    public void setTicketID_1(String ticketID_1) {
        this.ticketID_1 = ticketID_1;
    }

    public String getTicketID_2() {
        return ticketID_2;
    }

    public void setTicketID_2(String ticketID_2) {
        this.ticketID_2 = ticketID_2;
    }

    public String getTicketID_3() {
        return ticketID_3;
    }

    public void setTicketID_3(String ticketID_3) {
        this.ticketID_3 = ticketID_3;
    }

    public String getTicketID_4() {
        return ticketID_4;
    }

    public void setTicketID_4(String ticketID_4) {
        this.ticketID_4 = ticketID_4;
    }

    public String getTicketID_5() {
        return ticketID_5;
    }

    public void setTicketID_5(String ticketID_5) {
        this.ticketID_5 = ticketID_5;
    }
}

