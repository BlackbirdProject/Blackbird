package com.madblackbird.blackbird.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.madblackbird.blackbird.R;
import com.madblackbird.blackbird.adapter.ItineraryRecyclerViewAdapter;
import com.madblackbird.blackbird.callback.OnTripLoadCallback;
import com.madblackbird.blackbird.dataClasses.Itinerary;
import com.madblackbird.blackbird.dataClasses.Plan;
import com.madblackbird.blackbird.service.TripManagerService;

import java.util.List;

import butterknife.BindView;

public class TripItinerariesActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view_itineraries)
    RecyclerView recyclerViewItineraries;

    private ItineraryRecyclerViewAdapter itineraryRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_itineraries);
        Intent intent = getIntent();
        double latitude = intent.getDoubleExtra("latitude", 0);
        double longitude = intent.getDoubleExtra("longitude", 0);
        if (latitude != 0 && longitude != 0)
            TripManagerService.findRoute(new LatLng(40.447216, -3.692497),
                    new LatLng(latitude, longitude),
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
        recyclerViewItineraries.setAdapter(itineraryRecyclerViewAdapter);
    }

}
