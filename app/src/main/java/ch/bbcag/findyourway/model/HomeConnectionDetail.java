package ch.bbcag.findyourway.model;

import java.util.Date;
import java.util.List;

public class HomeConnectionDetail {
    private Stop From;
    private Stop To;
    private String DurationTotal;
    private Integer Transfers;
    private List<HomeConnection> Sections;

    public HomeConnectionDetail(Stop from, Stop to, String durationTotal, Integer transfers, List<HomeConnection> sections) {
        From = from;
        To = to;
        DurationTotal = durationTotal;
        Transfers = transfers;
        Sections = sections;
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
}
