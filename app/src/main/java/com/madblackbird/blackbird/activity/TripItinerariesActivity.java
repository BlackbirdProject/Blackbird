package com.madblackbird.blackbird.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.madblackbird.blackbird.R;
import com.madblackbird.blackbird.adapter.ItineraryRecyclerViewAdapter;
import com.madblackbird.blackbird.callback.OnTripLoadCallback;
import com.madblackbird.blackbird.dataClasses.Itinerary;
import com.madblackbird.blackbird.dataClasses.OTPPlace;
import com.madblackbird.blackbird.dataClasses.Plan;
import com.madblackbird.blackbird.service.LocationService;
import com.madblackbird.blackbird.service.TripManagerService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TripItinerariesActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view_itineraries)
    RecyclerView recyclerViewItineraries;

    private ItineraryRecyclerViewAdapter itineraryRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_itineraries);
        ButterKnife.bind(this);
        recyclerViewItineraries.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();
        OTPPlace otpFrom = (OTPPlace) intent.getSerializableExtra("from");
        OTPPlace otpTo = (OTPPlace) intent.getSerializableExtra("to");
        LocationService locationService = new LocationService(this);
        LatLng from;
        if (otpFrom == null)
            from = locationService.getCurrentLocation();
        else
            from = otpFrom.getLatLng();
        if (from == null)
            from = new LatLng(40.447216, -3.692497);
        if (otpTo != null)
            TripManagerService.findRoute(
                    this,
                    from,
                    otpTo.getLatLng(),
                    new OnTripLoadCallback() {
                        @Override
                        public void onItineraryLoaded(Plan plan) {
                            populateRecycleView(plan.getItineraries());
                        }

                        @Override
                        public void onLoadError() {

                        }
                    });
    }

    private void populateRecycleView(List<Itinerary> itineraries) {
        itineraryRecyclerViewAdapter = new ItineraryRecyclerViewAdapter(itineraries);
        itineraryRecyclerViewAdapter.setOnClickListener(v -> {
            int pos = recyclerViewItineraries.indexOfChild(v);
            Itinerary itinerary = itineraryRecyclerViewAdapter.getItinerary(pos);
            Intent detailsIntent = new Intent(TripItinerariesActivity.this, TripDetailsActivity.class);
            detailsIntent.putExtra("itinerary", itinerary);
            startActivity(detailsIntent);
        });
        recyclerViewItineraries.setAdapter(itineraryRecyclerViewAdapter);
    }

}
