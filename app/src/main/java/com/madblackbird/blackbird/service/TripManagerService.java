package com.madblackbird.blackbird.service;

import com.google.android.libraries.places.api.model.Place;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.madblackbird.blackbird.callback.OnTripLoadCallback;
import com.madblackbird.blackbird.dataClasses.Itinerary;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class TripManagerService {

    private static final Gson gson = new Gson();

    private static void findRoute(Place from, Place to, final OnTripLoadCallback callback) {
        RequestParams requestParams = new RequestParams();
        requestParams.add("fromPlace", from.getLatLng().latitude + ", " + from.getLatLng().longitude);
        requestParams.add("toPlace", to.getLatLng().latitude + ", " + to.getLatLng().longitude);
        requestParams.add("time", "7:02am");
        requestParams.add("date", "04-18-2019");
        requestParams.add("mode", "TRANSIT,WALK");
        requestParams.add("maxWalkDistance", "1000");
        requestParams.add("arriveBy", "false");
        OTPRestApi.get("plan", requestParams, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject jsonObject = response.getJSONObject("plan").getJSONArray("itineraries").getJSONObject(0);
                    Itinerary itinerary = gson.fromJson(jsonObject.toString(), Itinerary.class);
                    callback.onItineraryLoaded(itinerary);
                } catch (JSONException e) {
                    callback.onLoadError();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                callback.onLoadError();
            }

        });
    }

}
