package ch.bbcag.findyourway.views;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;

import java.util.Arrays;
import java.util.List;

import ch.bbcag.findyourway.R;
import ch.bbcag.findyourway.dal.HomeDataSource;
import ch.bbcag.findyourway.helper.TransportOpendataJsonParser;
import ch.bbcag.findyourway.model.Location;

/**
 * Fragment für den Search-Tab
 */
public class TabHomeFragment extends android.support.v4.app.Fragment implements OnMapReadyCallback {
    private static final String TRANSPORT_OPENDATA_LOCATIONS_API_URL = "http://transport.opendata.ch/v1/locations";
    private static final String TRANSPORT_OPENDATA_CONNECTIONS_API_URL = "http://transport.opendata.ch/v1/connections";

    private GoogleMap mGoogleMap;
    protected MapView mMapView;
    private View mView;

    private boolean mLocationPermissionGranted;
    private final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 111;
    private LocationManager locationManager;

    private android.location.Location lastLocation;

    private boolean firstTimeCallingLocation = true;

    private LocationListAdapter locationListAdapter;
    private HomeDataSource homeDataSource;
    private AutoCompleteTextView autoCompleteTextView;
    private Location from;

    public TabHomeFragment() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);

        getLocationPermission();

        autoCompleteTextView = mView.findViewById(R.id.locationDropdown);
        setHome();

        return mView;
    }

    private void setupLocationDropdown() {
        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 2) {
                    getLocationsByString(s.toString());
                }
            }
        });

        autoCompleteTextView.setOnItemClickListener(createOnItemClickListenerForDropdown());
    }

    private AdapterView.OnItemClickListener createOnItemClickListenerForDropdown() {
        return (parent, view, position, id) -> {
            Location selected = (Location)parent.getItemAtPosition(position);
            homeDataSource.open();
            homeDataSource.deleteAll();
            homeDataSource.createHomeLocation(selected.getId(), selected.getName());
            homeDataSource.close();

            this.from = selected;
        };
    }

    private AdapterView.OnItemClickListener createOnItemClickListenerForLocation() {
        return (parent, view, position, id) -> {
            Intent intent = new Intent(getContext(), HomeDetailActivity.class);
            Location selected = (Location)parent.getItemAtPosition(position);
            intent.putExtra("fromId", from.getId());
            intent.putExtra("toId", selected.getId());
            intent.putExtra("fromName", from.getName());
            intent.putExtra("toName", selected.getName());
            startActivity(intent);
        };
    }

    private void getLocationsByString(String str) {
        if (getView() == null || getContext() == null){
            return;
        }

        String url = TRANSPORT_OPENDATA_LOCATIONS_API_URL + "?query=" + str;
        final RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        final List<Location> locations = TransportOpendataJsonParser.createLocationsFromJsonString(response);
                        AutoCompleteTextView actv = getView().findViewById(R.id.locationDropdown);
                        ArrayAdapter<Location> adapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item, locations);
                        actv.setAdapter(adapter);
                    } catch (JSONException e) {
                        generateAlertDialog();
                    }
                }, error -> generateAlertDialog());
        queue.add(stringRequest);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        homeDataSource = new HomeDataSource(getContext());
    }

    private Location getHomeLocation(){
        homeDataSource.open();
        List<Location> homeLocations = homeDataSource.getAllHomeLocations();
        homeDataSource.close();

        if (homeLocations.toArray().length > 0){
            return homeLocations.get(0);
        }else{
            return null;
        }
    }

    // TODO: Change this from location to connection
    private void getConnectionsByCoordinates(android.location.Location location) {
        if (getContext() == null || getView() == null) {
            return;
        }

        Context context = getContext();
        View view = getView();

        String url = TRANSPORT_OPENDATA_LOCATIONS_API_URL + "?x=" + location.getLongitude() + "&y=" + location.getLatitude();
        final RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        final List<Location> locations = TransportOpendataJsonParser.createLocationsFromJsonString(response);
                        mGoogleMap.clear();
                        for (final Location location1: locations) {
                            // TODO: Change this no foreach only on click
                            //AddMarkerOnMap(location1);$
                            locationListAdapter = new LocationListAdapter(context, locations);
                            ListView locationList = view.findViewById(R.id.locationList);
                            locationList.setAdapter(locationListAdapter);
                            locationListAdapter.notifyDataSetChanged();
                            locationList.setOnItemClickListener(createOnItemClickListenerForLocation());
                        }
                    } catch (JSONException e) {
                        generateAlertDialog();
                    }
                }, error -> generateAlertDialog());
        queue.add(stringRequest);
    }

    private void AddMarkerOnMap(Location location) {
        mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(location.getCoordinates().getX(), location.getCoordinates().getY())).title(location.getName()));
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMapView = mView.findViewById(R.id.mapView);
        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }

        setupLocationDropdown();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (getContext() == null) {
            return;
        }

        MapsInitializer.initialize(getContext());

        mGoogleMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        updateLocationUI();

        getDeviceLocation(true);
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[] {
                            android.Manifest.permission.ACCESS_FINE_LOCATION
                    },
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION:
            {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }

        updateLocationUI();
    }

    private void updateLocationUI() {
        if (mGoogleMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mGoogleMap.setMyLocationEnabled(true);
                mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mGoogleMap.setMyLocationEnabled(false);
                mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
                lastLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    /**
     * Holt die aktuelle Position des Gerätes, falls kein genauer Wert bestimmt werden kann => return null
     * @param setCamera Bool ob Kameraposition der Map gesetzt werden soll
     */
    private void getDeviceLocation(boolean setCamera) {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                if (locationManager != null) {
                    android.location.Location passiveLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                    if (passiveLocation != null) {
                        android.location.Location loc = passiveLocation;
                        lastLocation = loc;
                        if (setCamera) {
                            setCameraOnMap(lastLocation, 16);
                        }
                    } else {
                        lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);;
                        if (setCamera) {
                            setCameraOnMap(lastLocation, 16);
                        }
                    }

                    getConnectionsByCoordinates(lastLocation);
                } else {
                    // standard location
                    lastLocation = null;
                }

                firstTimeCallingLocation = false;
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void setCameraOnMap(android.location.Location location, int zoom) {
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(location.getLatitude(),
                        location.getLongitude()), zoom));
    }

    @SuppressLint("MissingPermission")
    public void onResume() {
        super.onResume();

        if (firstTimeCallingLocation) {
            return;
        }

        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(android.location.Location location) {
                getDeviceLocation(false);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        if (mLocationPermissionGranted) {
            locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 30000, 10, locationListener);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (locationListAdapter == null || autoCompleteTextView == null) {
                return;
            }

            setHome();
            locationListAdapter.notifyDataSetChanged();
        }
    }

    private void setHome(){
        Location home = getHomeLocation();
        if (home != null){
            autoCompleteTextView.setText(home.getName());
            autoCompleteTextView.dismissDropDown();
            this.from = home;
        }
    }

    /**
     * Setzt den Type der Location anhand seines Präfix
     * @param str Stations Name
     * @param location Location auf dem der Type gesetzt werden soll
     */
    private void SetLocationType(String str, Location location) {
        String[] trains = {
                "I",
                "R",
                "S",
                "V",
                "E"
        };
        String boat = "BAT";

        String firstLetter = str.substring(0, 1);

        if (Arrays.asList(trains).contains(firstLetter)) {
            location.setType(0);
            return;
        } else if (str.startsWith(boat)) {
            location.setType(2);
        } else {
            location.setType(1);
        }
    }
}