package com.madblackbird.blackbird.service;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class UberRestApi {

    private final String BASE_URL = "https://api.uber.com/v1.2/";

    private AsyncHttpClient client;

    public UberRestApi(String authToken) {
        client = new AsyncHttpClient();
        client.addHeader("Authorization", "Token " + authToken);
    }

    public void get(String relativeUrl, RequestParams requestParams, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(relativeUrl), requestParams, responseHandler);
    }

    private String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

}
