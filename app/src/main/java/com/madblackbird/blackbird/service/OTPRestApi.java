package com.madblackbird.blackbird.service;

import android.content.res.Resources;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.madblackbird.blackbird.R;

public class OTPRestApi {

    private static final String BASE_URL =
            Resources.getSystem().getString(R.string.otp_server_url) +
            "/otp/routers/default/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams requestParams, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), requestParams, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }

}
