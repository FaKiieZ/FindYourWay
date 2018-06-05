package ch.bbcag.findyourway.model;

import java.util.ArrayList;
import java.util.List;

public class Location {
    private Integer Id;
    private int Type; // 0: Zug, 1: Bus, 2: Schiff
    private String Name;
    private Coordinates Coordinates;
    private Integer Distance;
    private List<Connection> Connections = new ArrayList<>();
    private boolean IsFavourite;

    public Location(){

    }

    public Location(Integer id, int type, String name){
        setId(id);
        setType(type);
        setName(name);
    }

    public Integer getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Integer getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public ch.bbcag.findyourway.model.Coordinates getCoordinates() {
        return Coordinates;
    }

    public void setCoordinates(ch.bbcag.findyourway.model.Coordinates coordinates) {
        Coordinates = coordinates;
    }

    public Integer getDistance() {
        return Distance;
    }

    public void setDistance(Integer distance) {
        Distance = distance;
    }

    public List<Connection> getConnections() {
        return Connections;
    }

    public void setConnections(List<Connection> connections) {
        Connections = connections;
    }

    public String toString(){
        if (getDistance() == null){
            return this.Name;
        }
        return this.Name + " " + this.Distance + "m";
    }

    public boolean isFavourite() {
        return IsFavourite;
    }

    public void setFavourite(boolean favourite) {
        IsFavourite = favourite;
    }
}
