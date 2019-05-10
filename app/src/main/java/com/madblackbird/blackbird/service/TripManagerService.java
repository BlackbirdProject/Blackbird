package com.madblackbird.blackbird.service;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.madblackbird.blackbird.R;
import com.madblackbird.blackbird.callback.OnTripLoadCallback;
import com.madblackbird.blackbird.dataClasses.TripPlan;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class TripManagerService {

    private static final Gson gson = new Gson();

    public static void findRoute(Context context, LatLng from, LatLng to, final OnTripLoadCallback callback) {
        RequestParams requestParams = new RequestParams();
        requestParams.add("fromPlace", from.latitude + ", " + from.longitude);
        requestParams.add("toPlace", to.latitude + ", " + to.longitude);
        requestParams.add("time", "7:02am");
        requestParams.add("date", "04-18-2019");
        requestParams.add("mode", "TRANSIT,WALK");
        requestParams.add("maxWalkDistance", "1000");
        requestParams.add("arriveBy", "false");
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

}
