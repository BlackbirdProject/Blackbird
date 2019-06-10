package com.madblackbird.blackbird.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.libraries.places.api.Places;
import com.madblackbird.blackbird.R;
import com.madblackbird.blackbird.adapter.PlacesAutoCompleteAdapter;
import com.madblackbird.blackbird.dataClasses.OTPPlace;
import com.madblackbird.blackbird.dataClasses.OTPTime;
import com.madblackbird.blackbird.service.LocationService;

import java.util.Calendar;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlacesAutocompleteActivity extends AppCompatActivity {

    @BindView(R.id.origin_search)
    EditText txtOriginSearch;
    @BindView(R.id.place_search)
    EditText txtDestination;
    @BindView(R.id.img_autocomplete_settings)
    ImageView imgEditPreferences;
    @BindView(R.id.img_autocomplete_time)
    ImageView imgEditTime;
    @BindView(R.id.img_autocomplete_date)
    ImageView imgEditDate;
    @BindView(R.id.layout_edit_time_type)
    RelativeLayout layoutEditTimeType;
    @BindView(R.id.lbl_autocomplete_selected_time)
    TextView lblSelectedTime;
    @BindView(R.id.lbl_autocomplete_time_type)
    TextView lblTimeType;
    @BindView(R.id.img_autocomplete_back)
    ImageView imgGoBack;

    private PlacesAutoCompleteAdapter mAutoCompleteAdapter;
    private RecyclerView recyclerView;
    private OTPPlace from;
    private OTPTime otpTime;

    private LocationService locationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places_autocomplete);
        ButterKnife.bind(this);
        locationService = new LocationService(this);
        boolean myLocation = getIntent().getBooleanExtra("myLocation", false);
        otpTime = new OTPTime();
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
                onOriginFocus();
            }
        });
        imgEditDate.setOnClickListener(v -> changeDate());
        imgEditTime.setOnClickListener(v -> changeTime());
        imgEditPreferences.setOnClickListener(v -> showPreferencesDialog());
        layoutEditTimeType.setOnClickListener(v -> showPreferencesDialog());
        imgGoBack.setOnClickListener(v -> onBackPressed());
        mAutoCompleteAdapter = new PlacesAutoCompleteAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAutoCompleteAdapter.setClickListener(destinationClickListener);
        recyclerView.setAdapter(mAutoCompleteAdapter);
        mAutoCompleteAdapter.notifyDataSetChanged();
        updateSelectedTime();
        if (!myLocation && txtOriginSearch.requestFocus()) {
            onOriginFocus();
        } else {
            from = new OTPPlace("My Location", "Current location", locationService.getCurrentLocation());
        }
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

    private void onOriginFocus() {
        recyclerView.setVisibility(View.GONE);
        mAutoCompleteAdapter.setClickListener(originClickListener);
        txtOriginSearch.setHintTextColor(Color.LTGRAY);
        txtOriginSearch.setHint("Origin");
    }

    private void showPreferencesDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_preferences, null);
        RadioGroup radioGroup = view.findViewById(R.id.radio_group_time_type);
        radioGroup.check(otpTime.isArriveBy() ? R.id.radio_button_arrive_by : R.id.radio_button_depart_at);
        new AlertDialog.Builder(this)
                .setView(view)
                .setTitle(getString(R.string.edit_preferences))
                .setPositiveButton(getString(R.string.ok), (dialog, which) -> {
                    otpTime.setArriveBy(
                            radioGroup.getCheckedRadioButtonId() == R.id.radio_button_arrive_by
                    );
                    updateSelectedTime();
                })
                .setNegativeButton(getString(R.string.cancel), null)
                .create()
                .show();
    }

    private void changeDate() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    otpTime.setYear(year);
                    otpTime.setMonth(month);
                    otpTime.setDayOfMonth(dayOfMonth);
                },
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(1546300800000L);
        datePickerDialog.show();
    }

    private void changeTime() {
        new TimePickerDialog(this,
                (view, hourOfDay, minute) -> {
                    otpTime.setHour(hourOfDay);
                    otpTime.setMinute(minute);
                    updateSelectedTime();
                },
                GregorianCalendar.HOUR_OF_DAY,
                GregorianCalendar.MINUTE,
                true).show();
    }

    @SuppressLint("DefaultLocale")
    private void updateSelectedTime() {
        lblSelectedTime.setText(String.format("%02d:%02d", otpTime.getHour(), otpTime.getMinute()));
        lblTimeType.setText(otpTime.isArriveBy() ? R.string.arrive_by : R.string.depart_at);
    }

    private PlacesAutoCompleteAdapter.ClickListener destinationClickListener = place -> {
        Intent intent = new Intent();
        if (from == null) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.no_origin_dialog_title)
                    .setMessage(R.string.no_origin_dialog_body)
                    .setPositiveButton(getString(R.string.ok), null)
                    .create()
                    .show();
        } else {
            if (from.latLng() != null) {
                intent.putExtra("from", from);
            }
            if (place.getLatLng() != null) {
                intent.putExtra("to", new OTPPlace(place.getId(), place.getName(), place.getAddress(), place.getLatLng()));
            }
            intent.putExtra("time", otpTime);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    };

    private PlacesAutoCompleteAdapter.ClickListener originClickListener = place -> {
        txtOriginSearch.setText(place.getName());
        from = new OTPPlace(place.getName(), place.getAddress(), place.getLatLng());
        txtDestination.requestFocus();
    };

}
