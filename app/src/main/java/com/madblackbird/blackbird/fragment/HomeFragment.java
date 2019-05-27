package com.madblackbird.blackbird.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.madblackbird.blackbird.R;
import com.madblackbird.blackbird.activity.PlacesAutocompleteActivity;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap googleMap;

    @BindView(R.id.search_box_places_autocomplete)
    EditText searchBox;

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
                startActivity(new Intent(getContext(), PlacesAutocompleteActivity.class)));
        FirebaseAuth.getInstance().signInAnonymously();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame, new HomeFragment());
        transaction.replace(R.id.content_frame, new HomeFragment(), "homeFragment");
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }
}
