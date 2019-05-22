package com.madblackbird.blackbird.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.maps.android.PolyUtil;
import com.madblackbird.blackbird.R;
import com.madblackbird.blackbird.adapter.TripDetailsAdapter;
import com.madblackbird.blackbird.dataClasses.Itinerary;
import com.madblackbird.blackbird.dataClasses.Leg;
import com.madblackbird.blackbird.dataClasses.LegGeometry;
import com.madblackbird.blackbird.service.TripManagerService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TripDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private Itinerary itinerary;

    @BindView(R.id.bottom_sheet_trip_details)
    LinearLayout bottomSheet;
    @BindView(R.id.recycler_view_trip_details)
    RecyclerView recyclerViewTripDetails;

    private TripDetailsAdapter tripDetailsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);
        ButterKnife.bind(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.trip_details_map);
        if (mapFragment != null)
            mapFragment.getMapAsync(this);
        Intent intent = getIntent();
        itinerary = (Itinerary) intent.getSerializableExtra("itinerary");
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setOnMapLoadedCallback(this::onMapLoaded);
        if (itinerary != null) {
            for (Leg leg : itinerary.getLegs()) {
                LegGeometry legGeometry = leg.getLegGeometry();
                if (legGeometry != null) {
                    int color = Color.BLACK;
                    try {
                        color = Color.parseColor("#" + leg.getRouteColor());
                    } catch (Exception ignored) {
                    }
                    googleMap.addPolyline(new PolylineOptions()
                            .addAll(PolyUtil.decode(legGeometry.getPoints()))
                            .color(color)
                            .width(20));
                }
            }
            recyclerViewTripDetails.setLayoutManager(new LinearLayoutManager(this));
            populateRecycleView(itinerary.getLegs());
        }
    }

    public void onMapLoaded() {
        if (itinerary != null) {
            LatLngBounds.Builder bounds = new LatLngBounds.Builder();
            for (Leg leg : itinerary.getLegs()) {
                LegGeometry legGeometry = leg.getLegGeometry();
                List<LatLng> latLngs = PolyUtil.decode(legGeometry.getPoints());
                for (LatLng latLng : latLngs) {
                    bounds.include(latLng);
                }
            }
            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 50));
        }
    }

    private void populateRecycleView(List<Leg> legs) {
        tripDetailsAdapter = new TripDetailsAdapter(TripManagerService.addStations(legs));
        recyclerViewTripDetails.setAdapter(tripDetailsAdapter);
    }

}
