package com.madblackbird.blackbird.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;
import com.madblackbird.blackbird.R;
import com.madblackbird.blackbird.activity.TripDetailsActivity;
import com.madblackbird.blackbird.adapter.ItineraryRecyclerViewAdapter;
import com.madblackbird.blackbird.callback.OnPriceEstimatesLoadCallback;
import com.madblackbird.blackbird.callback.OnTripLoadCallback;
import com.madblackbird.blackbird.dataClasses.Itinerary;
import com.madblackbird.blackbird.dataClasses.OTPPlace;
import com.madblackbird.blackbird.dataClasses.Plan;
import com.madblackbird.blackbird.dataClasses.PriceEstimates;
import com.madblackbird.blackbird.service.LocationService;
import com.madblackbird.blackbird.service.TripHistoryService;
import com.madblackbird.blackbird.service.TripManagerService;
import com.madblackbird.blackbird.service.UberTripService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TripItinerariesFragment extends Fragment {

    @BindView(R.id.recycler_view_itineraries)
    RecyclerView recyclerViewItineraries;

    private ItineraryRecyclerViewAdapter itineraryRecyclerViewAdapter;
    private TripHistoryService tripHistoryService;
    private List<Object> itineraries;

    private OTPPlace otpFrom;
    private OTPPlace otpTo;

    public TripItinerariesFragment() {

    }

    public TripItinerariesFragment(OTPPlace otpFrom, OTPPlace otpTo) {
        this.otpFrom = otpFrom;
        this.otpTo = otpTo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_trip_itineraries, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, getView());
        tripHistoryService = new TripHistoryService();
        recyclerViewItineraries.setLayoutManager(new LinearLayoutManager(getContext()));
        itineraries = new ArrayList<>();
        itineraryRecyclerViewAdapter = new ItineraryRecyclerViewAdapter(itineraries);
        itineraryRecyclerViewAdapter.setOnClickListener(v -> {
            int pos = recyclerViewItineraries.indexOfChild(v);
            Itinerary itinerary = (Itinerary) itineraryRecyclerViewAdapter.getItinerary(pos);
            tripHistoryService.addTrip(itinerary);
            Intent detailsIntent = new Intent(getContext(), TripDetailsActivity.class);
            detailsIntent.putExtra("itinerary", itinerary);
            startActivity(detailsIntent);
        });
        recyclerViewItineraries.setAdapter(itineraryRecyclerViewAdapter);
        initializeItineraries();
    }

    public void initializeItineraries() {
        LocationService locationService = new LocationService(getContext());
        UberTripService uberTripService = new UberTripService(getContext());
        LatLng from;
        if (otpFrom == null)
            from = locationService.getCurrentLocation();
        else
            from = otpFrom.getLatLng();
        if (from == null)
            from = new LatLng(40.447216, -3.692497);
        if (otpTo != null) {
            TripManagerService.findRoute(
                    getContext(),
                    from,
                    otpTo.getLatLng(),
                    new OnTripLoadCallback() {
                        @Override
                        public void onItineraryLoaded(Plan plan) {
                            if (plan != null) {
                                itineraries.addAll(plan.getItineraries());
                                itineraryRecyclerViewAdapter.notifyDataSetChanged();
                            } else {
                                // TODO: No available route
                            }
                        }

                        @Override
                        public void onLoadError() {

                        }
                    });
            uberTripService.priceEstimate(
                    from,
                    otpTo.getLatLng(),
                    new OnPriceEstimatesLoadCallback() {
                        @Override
                        public void onLoad(PriceEstimates priceEstimates) {
                            itineraries.addAll(priceEstimates.getPriceEstimates());
                            itineraryRecyclerViewAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onLoadError() {

                        }
                    }
            );

        }
    }

}
