package ch.bbcag.findyourway.model;

import java.util.List;

public class HomeConnectionDetail {
    private Stop From;
    private Stop To;
    private String DurationTotal;
    private Integer Transfers;
    private List<HomeConnection> Sections;
    private String JsonString;

    public HomeConnectionDetail(Stop from, Stop to, String durationTotal, Integer transfers, List<HomeConnection> sections, String json) {
        From = from;
        To = to;
        DurationTotal = durationTotal;
        Transfers = transfers;
        Sections = sections;
        JsonString = json;
    }

    public Stop getFrom() {
        return From;
    }

    public void setFrom(Stop from) {
        From = from;
    }

    public Stop getTo() {
        return To;
    }

    public void setTo(Stop to) {
        To = to;
    }

    public List<HomeConnection> getSections() {
        return Sections;
    }

    public void setSections(List<HomeConnection> sections) {
        Sections = sections;
    }

    public String getDurationTotal() {
        return DurationTotal.substring(3).substring(0, 5) + "h";
    }

    public void setDurationTotal(String durationTotal) {
        DurationTotal = durationTotal;
    }

    public Integer getTransfer() {
        return Transfers;
    }

    public void setTransfer(Integer transfer) {
        Transfers = transfer;
    }

    public String getJSON() {return JsonString;}
}
