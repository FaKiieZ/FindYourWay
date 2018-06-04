package ch.bbcag.findyourway.model;

import java.sql.Time;

public class Stop {
    private Location Station;
    private Time Arrival;
    private Time Departure;
    private Integer Delay;
    private Integer Platform;

    public Location getStation() {
        return Station;
    }

    public void setStation(Location station) {
        Station = station;
    }

    public Time getArrival() {
        return Arrival;
    }

    public void setArrival(Time arrival) {
        Arrival = arrival;
    }

    public Time getDeparture() {
        return Departure;
    }

    public void setDeparture(Time departure) {
        Departure = departure;
    }

    public Integer getDelay() {
        return Delay;
    }

    public void setDelay(Integer delay) {
        Delay = delay;
    }

    public Integer getPlatform() {
        return Platform;
    }

    public void setPlatform(int platform) {
        Platform = platform;
    }
    // prognosis
}
