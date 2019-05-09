package com.madblackbird.blackbird.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.madblackbird.blackbird.R;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startActivity(new Intent(this, TripDetailsActivity.class));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        if (!Places.isInitialized()) {
            Places.initialize(this, getString(R.string.google_maps_api_key));
        }
        PlacesClient placesClient = Places.createClient(this);
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG);
        assert autocompleteFragment != null;
        autocompleteFragment.setPlaceFields(placeFields);
        autocompleteFragment.setCountry("es");
        autocompleteFragment.setLocationRestriction(RectangularBounds.newInstance(
                new LatLng(39.938037, -4.815362),
                new LatLng(41.211806, -2.870480)
        ));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Intent intent = new Intent(MainActivity.this, TripItinerariesActivity.class);
                if (place.getLatLng() != null) {
                    intent.putExtra("latitude", place.getLatLng().latitude);
                    intent.putExtra("longitude", place.getLatLng().longitude);
                }
                startActivity(intent);
            }

            @Override
            public void onError(@NonNull Status status) {

            }
        });
    }

}
