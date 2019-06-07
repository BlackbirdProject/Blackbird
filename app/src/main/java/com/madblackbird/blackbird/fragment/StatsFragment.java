package com.madblackbird.blackbird.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.madblackbird.blackbird.R;
import com.madblackbird.blackbird.service.TripDatabaseService;

import butterknife.BindView;

public class StatsFragment extends Fragment {

    @BindView(R.id.lbl_travelled_kilometers)
    TextView lblTravelledKilometers;
    @BindView(R.id.lbl_saved_emissions)
    TextView lblSavedEmissions;

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
        tripDatabaseService = new TripDatabaseService();
        tripDatabaseService.getDistance(distance -> {
            lblTravelledKilometers.setText(String.valueOf(Math.round(distance)));
            lblSavedEmissions.setText(String.valueOf(co2EmissionsKm(distance)));
        });
    }

    private long co2EmissionsKm(double kilometers) {
        return Math.round((kilometers * 251) / 1000);
    }

}
