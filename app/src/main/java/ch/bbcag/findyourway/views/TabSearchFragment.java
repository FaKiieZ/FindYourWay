package ch.bbcag.findyourway.views;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.List;

import ch.bbcag.findyourway.R;
import ch.bbcag.findyourway.helper.TransportOpendataJsonParser;
import ch.bbcag.findyourway.model.Coordinates;
import ch.bbcag.findyourway.model.Location;

public class TabSearchFragment extends android.support.v4.app.Fragment {
    private static final String TRANSPORT_OPENDATA_LOCATIONS_API_URL = "http://transport.opendata.ch/v1/locations";

    public TabSearchFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        Coordinates coordinates = new Coordinates();
        coordinates.setX(46.947440);
        coordinates.setY(7.452570);
        getLocationsByCoordinates(coordinates);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    public void getLocationsByCoordinates(Coordinates coordinates) {
        String url = TRANSPORT_OPENDATA_LOCATIONS_API_URL + "?x=" + coordinates.getX() + "&y=" + coordinates.getY();
        final ArrayAdapter<Location> locationAdapter = new
                ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            List<Location> locations = TransportOpendataJsonParser.createLocationsFromJsonString(response);
                            locationAdapter.addAll(locations);
                            ListView locationList = getView().findViewById(R.id.locationList);
                            locationList.setAdapter(locationAdapter);
                            //progressBar.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            generateAlertDialog();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                generateAlertDialog();
            }
        });
        queue.add(stringRequest);
    }

    private void generateAlertDialog() {
        //progressBar.setVisibility(View.GONE);
        AlertDialog.Builder dialogBuilder;
        dialogBuilder = new AlertDialog.Builder(getContext());
        dialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Closes this activity
                getActivity().finish();
            }
        });
        dialogBuilder.setMessage("Error").setTitle("Error");
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }
}

