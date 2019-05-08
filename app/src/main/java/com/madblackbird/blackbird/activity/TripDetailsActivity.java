package com.madblackbird.blackbird.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.madblackbird.blackbird.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TripDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private BottomSheetBehavior bottomSheetBehavior;

    @BindView(R.id.bottom_sheet_trip_details)
    private LinearLayout bottomSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_details);
        ButterKnife.bind(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.trip_details_map);
        if (mapFragment != null)
            mapFragment.getMapAsync(this);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }
}
