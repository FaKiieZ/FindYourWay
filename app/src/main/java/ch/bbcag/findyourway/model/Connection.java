package ch.bbcag.findyourway.model;

import java.sql.Time;

public class Connection {
    private Location From;
    private Location To;
    private Time Duration;
    private String Service;

    public Location getFrom() {
        return From;
    }

    public void setFrom(Location from) {
        From = from;
    }

    public Location getTo() {
        return To;
    }

    public void setTo(Location to) {
        To = to;
    }

    public Time getDuration() {
        return Duration;
    }

    public void setDuration(Time duration) {
        Duration = duration;
    }

    public String getService() {
        return Service;
    }

    public void setService(String service) {
        Service = service;
    }
}
