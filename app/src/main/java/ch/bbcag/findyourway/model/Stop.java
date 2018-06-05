package ch.bbcag.findyourway.model;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Stop {
    private Location Station;
    private Date Departure;
    private Date Arrival;
    private Integer Delay;
    private String Platform;

    public Location getStation() {
        return Station;
    }

    public void setStation(Location station) {
        Station = station;
    }

    public String getDeparture() {
        if (Departure == null){
            return null;
        }

        DateFormat df = new SimpleDateFormat("HH:mm");
        return df.format(Departure);
    }

    public Integer getDelay() {
        return Delay;
    }

    public void setDelay(Integer delay) {
        Delay = delay;
    }

    public String getPlatform() {
        return Platform;
    }

    public void setArrival(Date arrival) {
        Arrival = arrival;
    }

    public void setPlatform(String platform) {
        Platform = platform;
    }

    public void setDeparture(Date departure) {
        Departure = departure;
    }

    public String getArrivalTime() {
        DateFormat df = new SimpleDateFormat("HH:mm");
        return df.format(Arrival);
    }

    // prognosis
}
