package com.madblackbird.blackbird.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.madblackbird.blackbird.R;
import com.madblackbird.blackbird.adapter.PlacesAutoCompleteAdapter;

public class PlacesAutocompleteActivity extends AppCompatActivity implements PlacesAutoCompleteAdapter.ClickListener {

    private PlacesAutoCompleteAdapter mAutoCompleteAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_autocomplete);

        Places.initialize(this, getResources().getString(R.string.google_maps_api_key));

        recyclerView = findViewById(R.id.places_recycler_view);
        ((EditText) findViewById(R.id.place_search)).addTextChangedListener(filterTextWatcher);

        mAutoCompleteAdapter = new PlacesAutoCompleteAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAutoCompleteAdapter.setClickListener(this);
        recyclerView.setAdapter(mAutoCompleteAdapter);
        mAutoCompleteAdapter.notifyDataSetChanged();
    }

    private TextWatcher filterTextWatcher = new TextWatcher() {
        public void afterTextChanged(Editable s) {
            if (!s.toString().equals("")) {
                mAutoCompleteAdapter.getFilter().filter(s.toString());
                if (recyclerView.getVisibility() == View.GONE) {
                    recyclerView.setVisibility(View.VISIBLE);
                }
            } else {
                if (recyclerView.getVisibility() == View.VISIBLE) {
                    recyclerView.setVisibility(View.GONE);
                }
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
    };

    @Override
    public void click(Place place) {
        Intent intent = new Intent(this, TripItinerariesActivity.class);
        if (place.getLatLng() != null) {
            intent.putExtra("latitude", place.getLatLng().latitude);
            intent.putExtra("longitude", place.getLatLng().longitude);
        }
        startActivity(intent);
    }

}
