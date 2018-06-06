package ch.bbcag.findyourway.model;

import java.util.Date;
import java.util.List;

public class HomeConnectionDetail {
    private Location From;
    private Location To;
    private Integer DurationTotal;
    private Integer Transfers;
    private List<HomeConnection> Sections;

    public HomeConnectionDetail(Location from, Location to, Integer durationTotal, Integer transfers, List<HomeConnection> sections) {
        From = from;
        To = to;
        DurationTotal = durationTotal;
        Transfers = transfers;
        Sections = sections;
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

    public List<HomeConnection> getSections() {
        return Sections;
    }

    public void setSections(List<HomeConnection> sections) {
        Sections = sections;
    }

    public Integer getDurationTotal() {
        return DurationTotal;
    }

    public void setDurationTotal(Integer durationTotal) {
        DurationTotal = durationTotal;
    }

    public Integer getTransfer() {
        return Transfers;
    }

    public void setTransfer(Integer transfer) {
        Transfers = transfer;
    }
}
