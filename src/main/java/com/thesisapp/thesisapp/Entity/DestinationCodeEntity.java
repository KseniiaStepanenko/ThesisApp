package com.thesisapp.thesisapp.Entity;

public class DestinationCodeEntity {
    String flyFrom;
    String flyTo;

    public DestinationCodeEntity(String flyFrom, String flyTo) {
        this.flyFrom = flyFrom;
        this.flyTo = flyTo;
    }

    public String getFlyFrom() {
        return flyFrom;
    }

    public void setFlyFrom(String flyFrom) {
        this.flyFrom = flyFrom;
    }

    public String getFlyTo() {
        return flyTo;
    }

    public void setFlyTo(String flyTo) {
        this.flyTo = flyTo;
    }
}
