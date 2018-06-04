package ch.bbcag.findyourway.helper;

import android.util.Log;

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
            String arrival = jsonObj.getString("arrival");
            String departure = jsonObj.getString("departure");
            if (arrival != "null"){
                stop.setArrival(new Time(formatter.parse(arrival).getTime()));
            }

            if (departure != "null"){
                stop.setDeparture(new Time(formatter.parse(departure).getTime()));
            }

        } catch (ParseException ex){

        }

        String delay = jsonObj.getString("delay");

        if (delay != "null"){
            stop.setDelay(Integer.parseInt(delay));
        }

        String platform = jsonObj.getString("platform");

        if (platform != "null"){
            stop.setPlatform(Integer.parseInt(platform));
        }
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
        try{
            if (id != null && id.length() > 0 && id != "null") {
                location.setId(Integer.parseInt(id));
                location.setName(jsonObj.getString("name"));
                location.setCoordinates(createCoordinatesFromJsonString(jsonObj.getString("coordinate")));
                if(jsonObj.getString("distance") != "null"){
                    location.setDistance(Integer.parseInt(jsonObj.getString("distance")));
                }
            }
        } catch (Exception ex){
            Log.d("JSONParser: ", ex.getMessage());
        }


        return location;
    }

    public static Coordinates createCoordinatesFromJsonString(String coordinatesJsonString) throws JSONException {
        JSONObject jsonObj = new JSONObject(coordinatesJsonString);
        Double x = Double.parseDouble(jsonObj.getString("x"));
        Double y = Double.parseDouble(jsonObj.getString("y"));
        Coordinates coordinates = new Coordinates(x, y);

        return coordinates;
    }

    public static List<Connection> createConnectionsFromJsonString(String connectionJsonString) throws JSONException {
        List<Connection> list = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(connectionJsonString);
        JSONArray stationsJson = jsonObject.getJSONArray("stationboard");
        for(int i = 0; i < stationsJson.length(); i++){
            Location from = createLocationFromJsonString(jsonObject.getJSONObject("station").toString());
            JSONObject row = stationsJson.getJSONObject(i);
            int index = row.getJSONArray("passList").length() -1;
            Location to = createLocationFromJsonString(row.getJSONArray("passList").getJSONObject(index).getJSONObject("station").toString());
            Time duration = null;
            String service = "";
            Integer departure = Integer.parseInt(row.getJSONObject("stop").getString("departureTimestamp"));
            list.add(new Connection(from, to, duration, service, departure));
        }
        return list;
    }
}
