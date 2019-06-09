package com.madblackbird.blackbird.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;
import com.madblackbird.blackbird.R;
import com.madblackbird.blackbird.adapter.TripDetailsAdapter;
import com.madblackbird.blackbird.dataClasses.Itinerary;
import com.madblackbird.blackbird.dataClasses.Leg;
import com.madblackbird.blackbird.dataClasses.LegGeometry;
import com.madblackbird.blackbird.dataClasses.OTPPlace;
import com.madblackbird.blackbird.service.LocationService;
import com.madblackbird.blackbird.service.TripDatabaseService;
import com.madblackbird.blackbird.service.TripManagerService;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TripDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private Itinerary itinerary;
    private OTPPlace placeTo;

    @BindView(R.id.bottom_sheet_trip_details)
    LinearLayout bottomSheet;
    @BindView(R.id.recycler_view_trip_details)
    RecyclerView recyclerViewTripDetails;
    @BindView(R.id.img_add_favourite)
    ImageView imgAddFavourite;
    @BindView(R.id.img_hybrid_switch_details)
    ImageView imgHybridSwitch;

    private TripDetailsAdapter tripDetailsAdapter;
    private TripDatabaseService tripDatabaseService;
    private LocationService locationService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);
        ButterKnife.bind(this);
        tripDatabaseService = new TripDatabaseService();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.trip_details_map);
        if (mapFragment != null)
            mapFragment.getMapAsync(this);
        Intent intent = getIntent();
        itinerary = (Itinerary) intent.getSerializableExtra("itinerary");
        placeTo = (OTPPlace) intent.getSerializableExtra("placeTo");
        imgAddFavourite.setOnClickListener(v -> addFavourite());
        locationService = new LocationService(this);
        imgHybridSwitch.setOnClickListener(v -> {
            if (googleMap != null) {
                if (googleMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL)
                    googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                else if (googleMap.getMapType() == GoogleMap.MAP_TYPE_HYBRID)
                    googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            }
        });
        tripDatabaseService.isPlaceFavorite(
                placeTo,
                isFavourite -> {
                    if (isFavourite)
                        imgAddFavourite.setImageDrawable(getDrawable(R.drawable.heart_pressed));
                });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setOnMapLoadedCallback(this::onMapLoaded);
        if (itinerary != null) {
            for (Leg leg : itinerary.getLegs()) {
                if (leg.getMode().equals("WALK"))
                    drawWalkPolyline(leg);
                else
                    drawTransitPolyline(leg);
            }
            recyclerViewTripDetails.setLayoutManager(new LinearLayoutManager(this));
            populateRecycleView(itinerary.getLegs());
        }
    }

    private void addFavourite() {
        tripDatabaseService.addFavourite(
                placeTo,
                () -> imgAddFavourite.setImageDrawable(getDrawable(R.drawable.heart_pressed)
                ));
    }

    private void drawTransitPolyline(Leg leg) {
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
                    .width(25));
        }
    }

    private void drawWalkPolyline(Leg leg) {
        LegGeometry legGeometry = leg.getLegGeometry();
        if (legGeometry != null) {
            PolylineOptions polyLineOptions = new PolylineOptions();
            polyLineOptions.addAll(PolyUtil.decode(legGeometry.getPoints()));
            polyLineOptions.width(25);
            polyLineOptions.color(Color.rgb(0, 178, 255));
            Polyline polyline = googleMap.addPolyline(polyLineOptions);
            List<PatternItem> pattern = Arrays.asList(new Dot(), new Gap(10f));
            polyline.setPattern(pattern);
        }

    }

    public void onMapLoaded() {
        if (locationService.checkPermission()) {
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        }
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
