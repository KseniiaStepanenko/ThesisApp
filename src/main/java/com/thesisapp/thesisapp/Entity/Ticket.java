package com.thesisapp.thesisapp.Entity;

import java.util.Date;

public class Ticket {
    String ticketCombinationID;
    Double price;
    Date localDepartureDateTime;
    Date localArrivalDateTime;
    String redirectLink;
    Date refreshTimeStamp;
    int ref_flightID;

    public Ticket() {
    }

    public String getTicketCombinationID() {
        return ticketCombinationID;
    }

    public void setTicketCombinationID(String ticketCombinationID) {
        this.ticketCombinationID = ticketCombinationID;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getLocalDepartureDateTime() {
        return localDepartureDateTime;
    }

    public void setLocalDepartureDateTime(Date localDepartureDateTime) {
        this.localDepartureDateTime = localDepartureDateTime;
    }

    public Date getLocalArrivalDateTime() {
        return localArrivalDateTime;
    }

    public void setLocalArrivalDateTime(Date localArrivalDateTime) {
        this.localArrivalDateTime = localArrivalDateTime;
    }

    public String getRedirectLink() {
        return redirectLink;
    }

    public void setRedirectLink(String redirectLink) {
        this.redirectLink = redirectLink;
    }

    public Date getRefreshTimeStamp() {
        return refreshTimeStamp;
    }

    public void setRefreshTimeStamp(Date refreshTimeStamp) {
        this.refreshTimeStamp = refreshTimeStamp;
    }

    public int getRef_flightID() {
        return ref_flightID;
    }

    public void setRef_flightID(int ref_flightID) {
        this.ref_flightID = ref_flightID;
    }
}



