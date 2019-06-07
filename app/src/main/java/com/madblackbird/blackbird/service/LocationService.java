package com.madblackbird.blackbird.service;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.maps.model.LatLng;
import com.madblackbird.blackbird.callback.LocationUpdatesCallback;

import java.util.List;

/**
 * Manages location requests through the app
 *
 * @author Cleanse Project
 */
public class LocationService {

    private final Context context;
    private LocationManager locationManager;

    /**
     * Class constructor, creates necessary services
     *
     * @param context Current activity
     */
    public LocationService(Context context) {
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        requestLocationUpdate();
    }

    /**
     * @return True if the permission is granted by user and is enabled
     */
    public boolean checkPermission() {
        if (!isLocationEnabled())
            return false;
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    public void askForLocationPermission() {
        if (!checkPermission()) {
            ActivityCompat.requestPermissions((Activity) context,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    50);
        }
    }

    /**
     * @return True if location is turned on
     */
    private boolean isLocationEnabled() {
        int locationMode = 0;
        try {
            locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return locationMode != Settings.Secure.LOCATION_MODE_OFF;
    }

    /**
     * Asks for current location
     */
    private void requestLocationUpdate() {
        if (checkPermission())
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

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
            });
    }

    /**
     * @return Last known location
     */
    public LatLng getCurrentLocation() {
        locationManager = (LocationManager) context.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        LatLng returnLocation = null;
        for (String provider : providers) {
            checkPermission();
            Location l = locationManager.getLastKnownLocation(provider);
            if (l == null)
                continue;
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy())
                bestLocation = l;
        }
        if (bestLocation != null)
            returnLocation = new LatLng(bestLocation.getLatitude(), bestLocation.getLongitude());
        return returnLocation;
    }

    /**
     * @param callback Called when location is changed
     */
    public void setLocationListener(LocationUpdatesCallback callback) {
        if (checkPermission()) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    callback.locationUpdate(location);
                    locationManager.removeUpdates(this);
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
            });
        }
    }


}
