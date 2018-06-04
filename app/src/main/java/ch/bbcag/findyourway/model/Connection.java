package ch.bbcag.findyourway.model;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Connection {
    private Location From;
    private Location To;
    private Time Duration;
    private String Service;
    private Date Departure;

    private String platform;

    public Connection() {

    }

    public Connection(Location from, Location to, Time duration, String service, Date departure){
        setFrom(from);
        setTo(to);
        setDuration(duration);
        setService(service);
        setDeparture(departure);
    }

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

    public String getPlatform() { return platform; }

    public void setPlatform(String platform) { this.platform = platform; }

    public String toString() {
        return getDeparture() + ": " + From.getName() + " --> " + To.getName();
    }

    public String getDeparture() {
        DateFormat df = new SimpleDateFormat("HH:mm");

        return df.format(Departure);
    }

    public void setDeparture(Date departure) {
        Departure = departure;
    }
}
