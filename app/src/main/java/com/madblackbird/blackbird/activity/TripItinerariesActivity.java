package com.madblackbird.blackbird.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.madblackbird.blackbird.R;
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

public class TripItinerariesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_itineraries);
    }

}
