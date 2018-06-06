package ch.bbcag.findyourway.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HomeConnectionDetail {
    private String Category;
    private String Number;
    private String To;
    private List<Stop> PassList;
    private String From;
    private String Platform;
    private Date Departure;
    private String Arrival;
    private Date Duration;


    public HomeConnectionDetail(String category, String number, String to, List<Stop> passList, String from, String platform, Date departure, String arrival, Date duration){
        setCategory(category);
        setNumber(number);
        setTo(to);
        setPassList(passList);
        setFrom(from);
        setPlatform(platform);
        setDeparture(departure);
        setArrival(arrival);
        setDuration(duration);
    }

    public String getNumber() {
        return Number;
    }

    public void setNumber(String number) {
        Number = number;
    }

    public String getTo() {
        return To;
    }

    public void setTo(String to) {
        To = to;
    }

    public List<Stop> getPassList() {
        return PassList;
    }

    public void setPassList(List<Stop> passList) {
        PassList = passList;
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }

    public String getPlatform() {
        return Platform;
    }

    public void setPlatform(String plattform) {
        Platform = plattform;
    }

    public String getDeparture() {
        DateFormat df = new SimpleDateFormat("HH:mm");

        return df.format(Departure);
    }

    public void setDeparture(Date departure) {
        Departure = departure;
    }

    public String getArrival() {
        return Arrival;
    }

    public void setArrival(String arrival) {
        Arrival = arrival;
    }

    public Date getDuration() {
        return Duration;
    }

    public void setDuration(Date duration) {
        Duration = duration;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }
}
