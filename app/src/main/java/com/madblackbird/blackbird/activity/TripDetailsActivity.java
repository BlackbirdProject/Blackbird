package com.madblackbird.blackbird.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.maps.android.PolyUtil;
import com.madblackbird.blackbird.R;
import com.madblackbird.blackbird.dataClasses.Itinerary;
import com.madblackbird.blackbird.dataClasses.Leg;
import com.madblackbird.blackbird.dataClasses.LegGeometry;
import com.madblackbird.blackbird.fragment.TripDetailsFragment;

import butterknife.ButterKnife;

public class TripDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private BottomSheetBehavior bottomSheetBehavior;
    private Itinerary itinerary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);
        ButterKnife.bind(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.trip_details_map);
        if (mapFragment != null)
            mapFragment.getMapAsync(this);
        TripDetailsFragment tripDetailsFragment = new TripDetailsFragment();
        tripDetailsFragment.show(getSupportFragmentManager(), tripDetailsFragment.getTag());
        Intent intent = getIntent();
        itinerary = (Itinerary) intent.getSerializableExtra("itinerary");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (itinerary != null) {
            for (Leg leg : itinerary.getLegs()) {
                LegGeometry legGeometry = leg.getLegGeometry();
                if (legGeometry != null) {
                    int color = Color.BLACK;
                    try {
                        color = Color.parseColor("#" + leg.getRouteColor());
                    } catch (Exception ignored) {
                    }
                    googleMap.addPolyline(new PolylineOptions().addAll(PolyUtil.decode(legGeometry.getPoints())).color(color));
                }
            }
        }
    }

}
