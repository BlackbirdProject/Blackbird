package com.madblackbird.blackbird.fragment;


import android.content.Intent;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.madblackbird.blackbird.R;
import com.madblackbird.blackbird.activity.TripDetailsActivity;
import com.madblackbird.blackbird.adapter.ItineraryRecyclerViewAdapter;
import com.madblackbird.blackbird.adapter.UberRecyclerViewAdapter;
import com.madblackbird.blackbird.callback.OnPriceEstimatesLoadCallback;
import com.madblackbird.blackbird.callback.OnTripLoadCallback;
import com.madblackbird.blackbird.dataClasses.Itinerary;
import com.madblackbird.blackbird.dataClasses.OTPPlace;
import com.madblackbird.blackbird.dataClasses.OTPTime;
import com.madblackbird.blackbird.dataClasses.Plan;
import com.madblackbird.blackbird.dataClasses.PriceEstimate;
import com.madblackbird.blackbird.dataClasses.PriceEstimates;
import com.madblackbird.blackbird.dataClasses.TimeEstimate;
import com.madblackbird.blackbird.service.LocationService;
import com.madblackbird.blackbird.service.TripDatabaseService;
import com.madblackbird.blackbird.service.TripManagerService;
import com.madblackbird.blackbird.service.UberTripService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TripItinerariesFragment extends Fragment {

    @BindView(R.id.recycler_view_itineraries)
    RecyclerView recyclerViewItineraries;
    @BindView(R.id.recycler_view_uber)
    RecyclerView recyclerViewUber;
    @BindView(R.id.lbl_uber)
    TextView lblUber;
    @BindView(R.id.img_itineraries_menu)
    ImageView imgOpenMenu;

    private TripDatabaseService tripDatabaseService;
    private UberTripService uberTripService;
    private OTPPlace otpFrom;
    private OTPPlace otpTo;
    private OTPTime otpTime;
    private boolean tripHistory;

    private ItineraryRecyclerViewAdapter itineraryRecyclerViewAdapter;
    private List<Itinerary> itineraries;

    private UberRecyclerViewAdapter uberRecyclerViewAdapter;
    private List<PriceEstimate> priceEstimates;
    private HashMap<String, TimeEstimate> timeEstimates;

    public TripItinerariesFragment() {
        tripHistory = true;
    }

    public TripItinerariesFragment(OTPPlace otpFrom, OTPPlace otpTo, OTPTime otpTime) {
        this.otpFrom = otpFrom;
        this.otpTo = otpTo;
        this.otpTime = otpTime;
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
        tripDatabaseService = new TripDatabaseService();
        uberTripService = new UberTripService(getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        if (tripHistory) {
            layoutManager.setReverseLayout(true);
            layoutManager.setStackFromEnd(true);
        }
        recyclerViewItineraries.setLayoutManager(layoutManager);
        recyclerViewUber.setLayoutManager(new LinearLayoutManager(getContext()));
        itineraries = new ArrayList<>();
        priceEstimates = new ArrayList<>();
        timeEstimates = new HashMap<>();
        uberRecyclerViewAdapter = new UberRecyclerViewAdapter(priceEstimates, timeEstimates);
        itineraryRecyclerViewAdapter = new ItineraryRecyclerViewAdapter(itineraries);
        itineraryRecyclerViewAdapter.setItinerariesClickListener((v, position) -> {
            Itinerary objItinerary = itineraryRecyclerViewAdapter.getItinerary(position);
            if (!tripHistory)
                tripDatabaseService.addTrip(objItinerary);
            Intent detailsIntent = new Intent(getContext(), TripDetailsActivity.class);
            detailsIntent.putExtra("itinerary", objItinerary);
            detailsIntent.putExtra("placeTo", otpTo);
            startActivity(detailsIntent);
        });
        DrawerLayout drawerLayout = getActivity().findViewById(R.id.drawer_layout);
        imgOpenMenu.setOnClickListener(v ->
                drawerLayout.openDrawer(GravityCompat.START));
        uberRecyclerViewAdapter.setItinerariesClickListener(v -> {
            PriceEstimate priceEstimate = uberRecyclerViewAdapter
                    .getPriceEstimate(recyclerViewUber.indexOfChild(v));
            uberTripService.openUberApp(priceEstimate, otpTo);
        });
        if (tripHistory)
            lblUber.setVisibility(View.GONE);
        recyclerViewItineraries.setAdapter(itineraryRecyclerViewAdapter);
        recyclerViewUber.setAdapter(uberRecyclerViewAdapter);
        if (tripHistory) {
            tripDatabaseService.getItineraries(itinerary -> {
                itineraries.add(itinerary);
                if (!tripHistory && itineraries.size() == 1)
                    itinerary.setExpanded(true);
                itineraryRecyclerViewAdapter.notifyDataSetChanged();
            });
        } else
            initializeItineraries();
    }

    public void initializeItineraries() {
        LocationService locationService = new LocationService(getContext());
        UberTripService uberTripService = new UberTripService(getContext());
        LatLng from;
        if (otpFrom == null)
            from = locationService.getCurrentLocation();
        else
            from = otpFrom.latLng();
        if (from == null)
            from = new LatLng(40.447216, -3.692497);
        if (otpTo != null) {
            TripManagerService.findRoute(
                    getContext(),
                    from,
                    otpTo.latLng(),
                    otpTime,
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
            uberTripService.timeEstimate(
                    otpTo.latLng(),
                    loadedTimeEstimates -> {
                        for (TimeEstimate timeEstimate : loadedTimeEstimates.getTimeEstimates()) {
                            timeEstimates.put(timeEstimate.getProductid(), timeEstimate);
                        }
                        uberRecyclerViewAdapter.notifyDataSetChanged();
                    }
            );
            uberTripService.priceEstimate(
                    from,
                    otpTo.latLng(),
                    new OnPriceEstimatesLoadCallback() {
                        @Override
                        public void onLoad(PriceEstimates returnPriceEstimates) {
                            priceEstimates.addAll(returnPriceEstimates.getPriceEstimates());
                            uberRecyclerViewAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onLoadError() {

                        }
                    }
            );

        }
    }

}
