package ch.bbcag.findyourway.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;

import java.util.List;

import ch.bbcag.findyourway.R;
import ch.bbcag.findyourway.helper.TransportOpendataJsonParser;
import ch.bbcag.findyourway.model.Coordinates;
import ch.bbcag.findyourway.model.Location;
import ch.bbcag.findyourway.model.Stop;

public class TabHomeFragment extends Fragment implements OnMapReadyCallback {
    private static final String TRANSPORT_OPENDATA_LOCATIONS_API_URL = "http://transport.opendata.ch/v1/locations";
    GoogleMap mGoogleMap;
    MapView mMapView;
    View mView;
    public TabHomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        return mView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            Coordinates coordinates = new Coordinates(46.947440, 7.452570);
            getLocationsByCoordinates(coordinates);
        }
    }

    public void getLocationsByCoordinates(Coordinates coordinates) {
        if (getView() == null || getContext() == null){
            return;
        }

        String url = TRANSPORT_OPENDATA_LOCATIONS_API_URL + "?x=" + coordinates.getX() + "&y=" + coordinates.getY();
        final ArrayAdapter<Location> locationAdapter = new
                ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        List<Location> locations = TransportOpendataJsonParser.createLocationsFromJsonString(response);
                        locationAdapter.addAll(locations);
                        ListView locationList = getView().findViewById(R.id.locationList);
                        locationList.setAdapter(locationAdapter);
                        //progressBar.setVisibility(View.GONE);
                    } catch (JSONException e) {
                        generateAlertDialog();
                    }
                }, error -> generateAlertDialog());
        queue.add(stringRequest);
    }

    private void generateAlertDialog() {
        //progressBar.setVisibility(View.GONE);
        AlertDialog.Builder dialogBuilder;
        dialogBuilder = new AlertDialog.Builder(getContext());
        dialogBuilder.setPositiveButton("Ok", (dialog, id) -> {
            // Closes this activity
            getActivity().finish();
        });
        dialogBuilder.setMessage("Error").setTitle("Error");
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        mMapView = mView.findViewById(R.id.mapView);
        if (mMapView != null){
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap){
        if (getContext() == null){
            return;
        }

        MapsInitializer.initialize(getContext());

        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        LatLng coordinates = new LatLng(46.947440, 7.452570);

        googleMap.addMarker(new MarkerOptions().position(coordinates));

        CameraPosition cameraPosition = CameraPosition.builder().target(coordinates).zoom(16).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}
