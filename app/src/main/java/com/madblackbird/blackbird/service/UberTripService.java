package com.madblackbird.blackbird.service;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.madblackbird.blackbird.R;
import com.madblackbird.blackbird.callback.OnPriceEstimatesLoadCallback;
import com.madblackbird.blackbird.callback.TimeEstimatesLoadCallback;
import com.madblackbird.blackbird.dataClasses.OTPPlace;
import com.madblackbird.blackbird.dataClasses.PriceEstimate;
import com.madblackbird.blackbird.dataClasses.PriceEstimates;
import com.madblackbird.blackbird.dataClasses.TimeEstimates;
import com.uber.sdk.android.rides.RideParameters;
import com.uber.sdk.android.rides.RideRequestDeeplink;
import com.uber.sdk.core.client.SessionConfiguration;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import cz.msebera.android.httpclient.Header;

public class UberTripService {

    private String authToken;
    private Context context;
    private UberRestApi uberRestApi;
    private Gson gson;
    private LocationService locationService;

    public UberTripService(Context context) {
        this.context = context;
        uberRestApi = new UberRestApi(context.getString(R.string.uber_server_token));
        gson = new Gson();
        locationService = new LocationService(context);
    }

    public void priceEstimate(LatLng start, LatLng end, OnPriceEstimatesLoadCallback callback) {
        RequestParams requestParams = new RequestParams();
        requestParams.add("start_latitude", String.valueOf(start.latitude));
        requestParams.add("start_longitude", String.valueOf(start.longitude));
        requestParams.add("end_latitude", String.valueOf(end.latitude));
        requestParams.add("end_longitude", String.valueOf(end.longitude));
        uberRestApi.get(
                "estimates/price",
                requestParams,
                new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        callback.onLoad(gson.fromJson(response.toString(), PriceEstimates.class));
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        callback.onLoadError();
                    }

                }
        );
    }

    public void timeEstimate(LatLng start, TimeEstimatesLoadCallback callback) {
        RequestParams requestParams = new RequestParams();
        requestParams.add("start_latitude", String.valueOf(start.latitude));
        requestParams.add("start_longitude", String.valueOf(start.longitude));
        uberRestApi.get(
                "estimates/time",
                requestParams,
                new JsonHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        callback.onTimeEstimatesLoad(gson.fromJson(response.toString(), TimeEstimates.class));
                    }

                }
        );
    }

    public void openUberApp(PriceEstimate priceEstimate, OTPPlace to) {
        try {
            Uri deeplink = Uri.parse("uber://?client_id=" + context.getString(R.string.uber_client_id) +
                    "&action=setPickup&pickup=my_location" +
                    "&dropoff[latitude]=" + to.getLat() +
                    "&dropoff[longitude]=" + to.getLon() +
                    "&dropoff[nickname]=" + URLEncoder.encode(to.getName(), "UTF-8") +
                    "&dropoff[formatted_address]=" + URLEncoder.encode(to.getAddressName(), "UTF-8") +
                    "&product_id=" + priceEstimate.getProductid());
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(deeplink);
            try {
                context.startActivity(intent);
            } catch (ActivityNotFoundException e) {
                RideRequestDeeplink rideRequestDeeplink = new RideRequestDeeplink.Builder(context)
                        .setRideParameters(new RideParameters.Builder()
                                .build())
                        .setSessionConfiguration(new SessionConfiguration.Builder()
                                .setClientId(context.getString(R.string.uber_client_id))
                                .setServerToken(context.getString(R.string.uber_server_token))
                                .build())
                        .build();
                rideRequestDeeplink.execute();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
