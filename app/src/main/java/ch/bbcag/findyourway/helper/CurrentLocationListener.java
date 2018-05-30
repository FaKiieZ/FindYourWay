package ch.bbcag.findyourway.helper;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.google.android.gms.location.LocationServices;

import ch.bbcag.findyourway.model.Coordinates;

public class CurrentLocationListener implements LocationListener {
    private Coordinates coordinates;

    public CurrentLocationListener(Location location){
        this.coordinates.setX(location.getLatitude());
        this.coordinates.setY(location.getLongitude());
    }

    @Override
    public void onLocationChanged(Location location) {
        coordinates = new Coordinates(location.getLatitude(), location.getLongitude());
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

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}
