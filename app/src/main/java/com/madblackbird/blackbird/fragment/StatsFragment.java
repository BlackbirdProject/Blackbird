package com.madblackbird.blackbird.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.madblackbird.blackbird.R;
import com.madblackbird.blackbird.service.TripDatabaseService;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StatsFragment extends Fragment {

    @BindView(R.id.lbl_travelled_kilometers)
    TextView lblTravelledKilometers;
    @BindView(R.id.lbl_saved_emissions)
    TextView lblSavedEmissions;
    @BindView(R.id.img_stats_menu)
    ImageView imgOpenMenu;

    private TripDatabaseService tripDatabaseService;

    public StatsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stats, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, getView());
        tripDatabaseService = new TripDatabaseService();
        tripDatabaseService.getDistance(distance -> {
            lblTravelledKilometers.setText(String.valueOf(Math.round(distance / 1000)));
            lblSavedEmissions.setText(String.valueOf(co2EmissionsKm(distance / 1000)));
        });
        DrawerLayout drawerLayout = getActivity().findViewById(R.id.drawer_layout);
        imgOpenMenu.setOnClickListener(v ->
                drawerLayout.openDrawer(GravityCompat.START));
    }

    private long co2EmissionsKm(double kilometers) {
        return Math.round((kilometers * 251) / 1000);
    }

}
