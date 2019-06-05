package com.madblackbird.blackbird.service;

import android.annotation.SuppressLint;
import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.madblackbird.blackbird.R;
import com.madblackbird.blackbird.callback.OnTripLoadCallback;
import com.madblackbird.blackbird.dataClasses.Leg;
import com.madblackbird.blackbird.dataClasses.OTPTime;
import com.madblackbird.blackbird.dataClasses.TripPlan;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class TripManagerService {

    private static final Gson gson = new Gson();

    public static void findRoute(Context context, LatLng from, LatLng to, OTPTime otpTime, final OnTripLoadCallback callback) {
        RequestParams requestParams = new RequestParams();
        requestParams.add("fromPlace", from.latitude + ", " + from.longitude);
        requestParams.add("toPlace", to.latitude + ", " + to.longitude);
        requestParams.add("time", formatTime(otpTime));
        requestParams.add("date", formatDate(otpTime));
        requestParams.add("mode", "TRANSIT,WALK");
        OTPRestApi.get(
                context.getString(R.string.otp_server_url),
                "plan",
                requestParams,
                new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        TripPlan tripPlan = gson.fromJson(response.toString(), TripPlan.class);
                        callback.onItineraryLoaded(tripPlan.getPlan());
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        callback.onLoadError();
                    }

                });
    }

    public static ArrayList<Object> addStations(List<Leg> legs) {
        ArrayList<Object> places = new ArrayList<>();
        for (int i = 0; i < legs.size(); i++) {
            Leg leg = legs.get(i);
            if (i == 0)
                places.add(leg.getFrom());
            places.add(leg);
            places.add(leg.getTo());
        }
        return places;
    }

    @SuppressLint("DefaultLocale")
    private static String formatTime(OTPTime otpTime) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mma");
        //return dateFormat.format(new Date(System.currentTimeMillis()));
        return String.format("%02d:%02d", otpTime.getHour(), otpTime.getMinute());
    }

    @SuppressLint("DefaultLocale")
    private static String formatDate(OTPTime otpTime) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        //return dateFormat.format(new Date(System.currentTimeMillis()));
        return String.format("%02d-%02d-%04d", otpTime.getMonth(), otpTime.getDayOfMonth(), otpTime.getYear());
    }

}
