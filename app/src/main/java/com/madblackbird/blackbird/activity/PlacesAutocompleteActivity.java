package com.madblackbird.blackbird.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.madblackbird.blackbird.R;
import com.madblackbird.blackbird.adapter.PlacesAutoCompleteAdapter;
import com.madblackbird.blackbird.dataClasses.OTPPlace;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlacesAutocompleteActivity extends AppCompatActivity {

    @BindView(R.id.origin_search)
    EditText txtOriginSearch;
    @BindView(R.id.place_search)
    EditText txtDestination;
    @BindView(R.id.layout_edit_preferences_autocomplete)
    RelativeLayout layoutEditPrefrences;

    private PlacesAutoCompleteAdapter mAutoCompleteAdapter;
    private RecyclerView recyclerView;
    private Place from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_autocomplete);
        ButterKnife.bind(this);
        txtDestination.requestFocus();

        Places.initialize(this, getResources().getString(R.string.google_maps_api_key));

        recyclerView = findViewById(R.id.places_recycler_view);
        txtDestination.addTextChangedListener(filterTextWatcher);
        txtOriginSearch.addTextChangedListener(filterTextWatcher);

        txtDestination.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                recyclerView.setVisibility(View.GONE);
                mAutoCompleteAdapter.setClickListener(destinationClickListener);
                txtOriginSearch.setHint(R.string.my_location);
            }
        });

        txtOriginSearch.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                recyclerView.setVisibility(View.GONE);
                mAutoCompleteAdapter.setClickListener(originClickListener);
                txtOriginSearch.setHint("");
            }
        });

        layoutEditPrefrences.setOnClickListener(v -> showPreferencesDialog());

        mAutoCompleteAdapter = new PlacesAutoCompleteAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAutoCompleteAdapter.setClickListener(destinationClickListener);
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

    private void showPreferencesDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_preferences, null);
        CheckBox checkBoxTransit = view.findViewById(R.id.checkbox_transit);
        new AlertDialog.Builder(this)
                .setView(view)
                .setTitle("Edit preferences")
                .setPositiveButton("OK", (dialog, which) -> {

                })
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private PlacesAutoCompleteAdapter.ClickListener destinationClickListener = place -> {
        Intent intent = new Intent();
        if (from != null && from.getLatLng() != null) {
            intent.putExtra("from", new OTPPlace(from.getName(), from.getLatLng()));
        }
        if (place.getLatLng() != null) {
            intent.putExtra("to", new OTPPlace(place.getName(), place.getLatLng()));
        }
        setResult(Activity.RESULT_OK, intent);
        finish();
    };

    private PlacesAutoCompleteAdapter.ClickListener originClickListener = place -> {
        txtOriginSearch.setText(place.getName());
        from = place;
        txtDestination.requestFocus();
    };

}
