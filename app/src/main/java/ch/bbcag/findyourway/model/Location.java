package ch.bbcag.findyourway.model;

import java.util.ArrayList;
import java.util.List;

public class Location {
    private int Id;
    private int Type;
    private String Name;
    private Coordinates Coordinates;
    private int Distance;
    private List<Connection> Connections = new ArrayList<>();

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getType() {
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

    public int getDistance() {
        return Distance;
    }

    public void setDistance(int distance) {
        Distance = distance;
    }

    public List<Connection> getConnections() {
        return Connections;
    }

    public void setConnections(List<Connection> connections) {
        Connections = connections;
    }
}
