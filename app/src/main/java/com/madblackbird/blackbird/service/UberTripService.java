package com.madblackbird.blackbird.service;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.madblackbird.blackbird.R;
import com.madblackbird.blackbird.callback.OnPriceEstimatesLoadCallback;
import com.madblackbird.blackbird.dataClasses.PriceEstimates;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class UberTripService {

    private String authToken;
    private Context context;
    private UberRestApi uberRestApi;
    private Gson gson;

    public UberTripService(Context context) {
        uberRestApi = new UberRestApi(context.getString(R.string.uber_server_token));
        gson = new Gson();
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

}
