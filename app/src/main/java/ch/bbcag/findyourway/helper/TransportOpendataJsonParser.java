package ch.bbcag.findyourway.helper;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ch.bbcag.findyourway.model.Connection;
import ch.bbcag.findyourway.model.ConnectionDetail;
import ch.bbcag.findyourway.model.Coordinates;
import ch.bbcag.findyourway.model.HomeConnectionDetail;
import ch.bbcag.findyourway.model.Location;
import ch.bbcag.findyourway.model.Stop;

/**
 * Dient zum Parsen einer JSON-Antwort der "TransportOpendata" API
 */
public class TransportOpendataJsonParser {

    /**
     * Erstellt ein @see Stop Objekt anhand eines JSON-String
     * @param stopJsonString JSON String
     * @return Neu erzeugtes Stop-Objekt
     * @throws JSONException Wirft JSONException beim Parsen des Strings
     */
    public static Stop createStopFromJsonString(String stopJsonString) throws JSONException {
        Stop stop = new Stop();
        JSONObject jsonObj = new JSONObject(stopJsonString);
        stop.setStation(createLocationFromJsonString(jsonObj.getString("station")));
        String arrival = jsonObj.getString("arrival");
        String departure = jsonObj.getString("departure");
        String delay = jsonObj.getString("delay");
        String platform = jsonObj.getString("platform");

        if (arrival != "null") {
            Date arrivalDate = new Date(Long.parseLong(jsonObj.getString("arrivalTimestamp")) * 1000L);
            stop.setArrival(arrivalDate);
        }

        if (departure != "null") {
            Date departureDate = new Date(Long.parseLong(jsonObj.getString("departureTimestamp")) * 1000L);
            stop.setDeparture(departureDate);
        }

        if (delay != "null") {
            stop.setDelay(Integer.parseInt(delay));
        }

        stop.setPlatform(platform);

        return stop;
    }

    /**
     * Erstellt eine Liste von Locations
     * @param locationJsonString String einer Location(API)
     * @return Liste mit Locations
     * @throws JSONException Wirft JSONException beim Parsen des Strings
     */
    public static List <Location> createLocationsFromJsonString(String locationJsonString) throws JSONException {
        List <Location> locations = new ArrayList < > ();
        JSONObject jsonObj = new JSONObject(locationJsonString);
        JSONArray stationsJson = jsonObj.getJSONArray("stations");
        for (int i = 0; i < stationsJson.length(); i++) {
            JSONObject row = stationsJson.getJSONObject(i);
            Location location = createLocationFromJsonString(row.toString());
            if (location.getId() != null) {
                locations.add(location);
            }
        }

        return locations;
    }

    /**
     * Erstellt ein Location-Objekt
     * @param locationJsonString JSON Location-String
     * @return  Location-Objekt
     * @throws JSONException Wirft JSONException beim Parsen des Strings
     */
    private static Location createLocationFromJsonString(String locationJsonString) throws JSONException {
        Location location = new Location();
        JSONObject jsonObj = new JSONObject(locationJsonString);
        String id = jsonObj.getString("id");
        try {
            if (id != null && id.length() > 0 && id != "null") {
                location.setId(Integer.parseInt(id));
                location.setName(jsonObj.getString("name"));
                location.setCoordinates(createCoordinatesFromJsonString(jsonObj.getString("coordinate")));
                if (jsonObj.getString("distance") != "null") {
                    location.setDistance(Integer.parseInt(jsonObj.getString("distance")));
                }
            }
        } catch (Exception ex) {
            Log.d("JSONParser: ", ex.getMessage());
        }


        return location;
    }

    private static Coordinates createCoordinatesFromJsonString(String coordinatesJsonString) throws JSONException {
        JSONObject jsonObj = new JSONObject(coordinatesJsonString);
        Double x = Double.parseDouble(jsonObj.getString("x"));
        Double y = Double.parseDouble(jsonObj.getString("y"));
        Coordinates coordinates = new Coordinates(x, y);

        return coordinates;
    }

    /**
     * Erstellt eine List von Connection anhand eines Stationboard-String
     * @param connectionJsonString JSON Stationboard-String
     * @return List mit Connection-Objekte
     * @throws JSONException Wirft JSONException beim Parsen des Strings
     */
    public static List <Connection> createConnectionsFromJsonString(String connectionJsonString) throws JSONException {
        List <Connection> list = new ArrayList < > ();
        JSONObject jsonObject = new JSONObject(connectionJsonString);
        JSONArray stationsJson = jsonObject.getJSONArray("stationboard");
        for (int i = 0; i < stationsJson.length(); i++) {
            Location from = createLocationFromJsonString(jsonObject.getJSONObject("station").toString());
            JSONObject row = stationsJson.getJSONObject(i);
            int index = row.getJSONArray("passList").length() - 1;
            Location to = createLocationFromJsonString(row.getJSONArray("passList").getJSONObject(index).getJSONObject("station").toString());
            Time duration = null;
            String service = "";
            String category = row.getString("category");
            String number = row.getString("number");
            String departureString = row.getJSONObject("stop").getString("departureTimestamp");
            String platform = row.getJSONObject("stop").getString("platform");
            Date departure = new Date(Long.parseLong(departureString) * 1000L);
            List < Stop > passList = new ArrayList < > ();
            // create passlist
            for (int z = 0; z < row.getJSONArray("passList").length(); z++) {
                String stop = row.getJSONArray("passList").get(z).toString();
                passList.add(createStopFromJsonString(stop));
            }

            String delayString = row.getJSONObject("stop").getString("delay");
            Integer delay = 0;
            if (delayString != "null") {
                delay = Integer.parseInt(delayString);
            }

            Coordinates coordinates = createCoordinatesFromJsonString(row.getJSONObject("stop").getJSONObject("station").getJSONObject("coordinate").toString());

            list.add(new Connection(from, to, duration, service, departure, category, number, platform, row.toString(), delay, coordinates));
        }
        return list;
    }

    /**
     * Erstellt eine List von Connection anhand eines Stationboard-String
     * @param connectionJsonString JSON Stationboard-String
     * @return List mit Connection-Objekte
     * @throws JSONException Wirft JSONException beim Parsen des Strings
     */
//    public static List<HomeConnectionDetail> createConnectionsForHomeFromJsonString(String connectionJsonString) throws JSONException {
//        List <Connection> list = new ArrayList < > ();
//        JSONObject jsonObject = new JSONObject(connectionJsonString);
//        JSONArray connectionsJson = jsonObject.getJSONArray("connections");
//        for (int i = 0; i < connectionsJson.length(); i++) {
//            JSONObject row = connectionsJson.getJSONObject(i);
//            String duration = row.getString("duration");
//            JSONArray sectionsJson = row.getJSONArray("sections");
//            for (int j = 0; j < sectionsJson.length(); j++){
//                JSONObject sectionJson = sectionsJson.getJSONObject(j);
//                JSONObject journeyJson = sectionJson.getJSONObject("journey");
//                String category = journeyJson.getString("category");
//                String number = journeyJson.getString("number");
//                String to = journeyJson.getString("to");
//                // create passlist
//                List<Stop> passList = new ArrayList<>();
//                JSONArray passListJson = journeyJson.getJSONArray("passList");
//                for (int z = 0; z < passListJson.length(); z++) {
//                    String stop = passListJson.get(z).toString();
//                    passList.add(createStopFromJsonString(stop));
//                }
//                JSONObject walkJson = sectionJson.getJSONObject("walk");
//            }
//            String category = row.getString("category");
//            String number = row.getString("number");
//            String departureString = row.getJSONObject("stop").getString("departureTimestamp");
//            String platform = row.getJSONObject("stop").getString("platform");
//            Date departure = new Date(Long.parseLong(departureString) * 1000L);
//            List <Stop> passList = new ArrayList < > ();
//            // create passlist
//            for (int z = 0; z < row.getJSONArray("passList").length(); z++) {
//                String stop = row.getJSONArray("passList").get(z).toString();
//                passList.add(createStopFromJsonString(stop));
//            }
//
//            String delayString = row.getJSONObject("stop").getString("delay");
//            Integer delay = 0;
//            if (delayString != "null") {
//                delay = Integer.parseInt(delayString);
//            }
//
//            Coordinates coordinates = createCoordinatesFromJsonString(row.getJSONObject("stop").getJSONObject("station").getJSONObject("coordinate").toString());
//
//            list.add(new Connection(from, to, duration, service, departure, category, number, platform, row.toString(), delay, coordinates));
//        }
//        return list;
//    }

    /**
     * Erstellt ein ConnectionDetail-Objekt
     * @param connectionString JSON String einer Connection
     * @return ConnectionDetail-Objekt
     * @throws JSONException
     */
    public static ConnectionDetail createConnectionDetailFromJsonString(String connectionString) throws JSONException {
        JSONObject jsonObject = new JSONObject(connectionString);
        List < Stop > passList = new ArrayList < > ();
        // create passlist
        for (int z = 0; z < jsonObject.getJSONArray("passList").length(); z++) {
            String stop = jsonObject.getJSONArray("passList").get(z).toString();
            passList.add(createStopFromJsonString(stop));
        }
        String category = jsonObject.getString("category");
        String number = jsonObject.getString("number");
        String to = jsonObject.getString("to");
        String from = jsonObject.getJSONObject("stop").getJSONObject("station").getString("name");
        String platform = jsonObject.getJSONObject("stop").getString("platform");
        Date departure = new Date(Long.parseLong(jsonObject.getJSONObject("stop").getString("departureTimestamp")) * 1000L);
        String arrival = passList.get(passList.size() - 1).getArrivalTime();
        return new ConnectionDetail(category, number, to, passList, from, platform, departure, arrival);
    }
}