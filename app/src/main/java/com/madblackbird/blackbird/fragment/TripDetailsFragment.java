package com.madblackbird.blackbird.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.madblackbird.blackbird.R;
import com.madblackbird.blackbird.adapter.TripDetailsAdapter;
import com.madblackbird.blackbird.dataClasses.Itinerary;
import com.madblackbird.blackbird.dataClasses.Leg;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TripDetailsFragment extends RoundedBottomSheetDialogFragment {

    private Itinerary itinerary;
    private TripDetailsAdapter tripDetailsAdapter;
    private RecyclerView recyclerViewTripDetails;

    public TripDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getArguments() != null)
            itinerary = (Itinerary) getArguments().getSerializable("itinerary");
        return inflater.inflate(R.layout.fragment_trip_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerViewTripDetails = view.findViewById(R.id.recycler_view_trip_details);
        recyclerViewTripDetails.setLayoutManager(new LinearLayoutManager(view.getContext()));
        populateRecycleView(itinerary.getLegs());
    }

    private void populateRecycleView(List<Leg> legs) {
        tripDetailsAdapter = new TripDetailsAdapter(legs);
        recyclerViewTripDetails.setAdapter(tripDetailsAdapter);
    }

}
