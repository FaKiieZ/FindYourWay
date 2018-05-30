package ch.bbcag.findyourway.helper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import ch.bbcag.findyourway.model.Connection;
import ch.bbcag.findyourway.model.Coordinates;
import ch.bbcag.findyourway.model.Location;
import ch.bbcag.findyourway.model.Stop;

public class TransportOpendataJsonParser {
    /*public static Connection createConnectionFromJsonString(String connectionJsonString) throws JSONException {
        Connection connection = new Connection();
        JSONObject jsonObj = new JSONObject(connectionJsonString);
        connection.setFrom(createjsonObj.getString("from"));
    }*/

    public static Stop createStopFromJsonString(String stopJsonString) throws JSONException {
        Stop stop = new Stop();
        JSONObject jsonObj = new JSONObject(stopJsonString);
        stop.setStation(createLocationFromJsonString(jsonObj.getString("station")));
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        try {
            stop.setArrival(new Time(formatter.parse(jsonObj.getString("arrival")).getTime()));
            stop.setDeparture(new Time(formatter.parse(jsonObj.getString("departure")).getTime()));
        } catch (ParseException ex){

        }

        stop.setDelay(Integer.parseInt(jsonObj.getString("delay")));
        stop.setPlatform(Integer.parseInt(jsonObj.getString("platform")));
        return stop;
    }


    public static List<Location> createLocationsFromJsonString(String locationJsonString) throws JSONException {
        List<Location> locations = new ArrayList<>();
        JSONObject jsonObj = new JSONObject(locationJsonString);
        JSONArray stationsJson = jsonObj.getJSONArray("stations");
        for (int i = 0; i < stationsJson.length(); i++) {
            JSONObject row = stationsJson.getJSONObject(i);
            Location location = createLocationFromJsonString(row.toString());
            if (location.getId() != null){
                locations.add(location);
            }
        }

        return locations;
    }


    public static Location createLocationFromJsonString(String locationJsonString) throws JSONException {
        Location location = new Location();
        JSONObject jsonObj = new JSONObject(locationJsonString);
        String id = jsonObj.getString("id");
        if (id != null && id.length() > 0 && id != "null") {
            location.setId(Integer.parseInt(id));
            location.setName(jsonObj.getString("name"));
            location.setCoordinates(createCoordinatesFromJsonString(jsonObj.getString("coordinate")));
            location.setDistance(Integer.parseInt(jsonObj.getString("distance")));
        }

        return location;
    }

    public static Coordinates createCoordinatesFromJsonString(String coordinatesJsonString) throws JSONException {
        Coordinates coordinates = new Coordinates();
        JSONObject jsonObj = new JSONObject(coordinatesJsonString);
        coordinates.setX(Double.parseDouble(jsonObj.getString("x")));
        coordinates.setY(Double.parseDouble(jsonObj.getString("y")));
        return coordinates;
    }
}
