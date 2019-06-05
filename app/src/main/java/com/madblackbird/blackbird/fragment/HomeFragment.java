package com.madblackbird.blackbird.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.madblackbird.blackbird.R;
import com.madblackbird.blackbird.activity.PlacesAutocompleteActivity;
import com.madblackbird.blackbird.dataClasses.OTPPlace;
import com.madblackbird.blackbird.service.LocationService;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.madblackbird.blackbird.activity.HomeActivity.PLACE_ACTIVITY;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap googleMap;

    @BindView(R.id.search_box_places_autocomplete)
    EditText searchBox;


    @BindView(R.id.btn_drawer_menu)
    ImageView btnDrawerMenu;

    private LocationService locationService;
    private boolean followUser;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getView());
        SupportMapFragment mapFragment = (SupportMapFragment) getFragmentManager()
                .findFragmentById(R.id.map_main_activity);
        if (mapFragment != null)
            mapFragment.getMapAsync(this);
        searchBox.setOnClickListener(v ->
                startActivityForResult(new Intent(getContext(), PlacesAutocompleteActivity.class), PLACE_ACTIVITY));
        DrawerLayout drawerLayout = getActivity().findViewById(R.id.drawer_layout);
        btnDrawerMenu.setOnClickListener(v ->
                drawerLayout.openDrawer(GravityCompat.START));
        locationService = new LocationService(getContext());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                OTPPlace otpFrom = (OTPPlace) data.getSerializableExtra("from");
                OTPPlace otpTo = (OTPPlace) data.getSerializableExtra("to");
                OTPTime otpTime = (OTPTime) data.getSerializableExtra("time");
                TripItinerariesFragment tripItinerariesFragment = new TripItinerariesFragment(otpFrom, otpTo, otpTime);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, tripItinerariesFragment, "tripItinerariesFragment")
                        .addToBackStack(null)
                        .commit();
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (locationService.checkPermission()) {
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            googleMap.setOnMyLocationButtonClickListener(() -> {
                followUser = true;
                return false;
            });
            locationService.setLocationListener(location -> {
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                if (followUser)
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11.0f));
            });
        }
    }
}
