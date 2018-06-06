package ch.bbcag.findyourway.model;

import java.util.List;

public class HomeConnection {
    private String Category;
    private String Number;
    private String To;
    private List<Stop> PassList;
    private Integer WalkDuration;

    public HomeConnection(String category, String number, String to, List<Stop> passList, Integer walkDuration) {

        Category = category;
        Number = number;
        To = to;
        PassList = passList;
        WalkDuration = walkDuration;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
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

    public Integer getWalkDuration() {
        return WalkDuration;
    }

    public void setWalkDuration(Integer walkDuration) {
        WalkDuration = walkDuration;
    }

}
