package com.madblackbird.blackbird.service;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class OTPRestApi {

    private static final String BASE_URL = "/otp/routers/default/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String siteUrl, String relativeUrl, RequestParams requestParams, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(siteUrl, relativeUrl), requestParams, responseHandler);
    }

    private static String getAbsoluteUrl(String siteurl, String relativeUrl) {
        return siteurl + BASE_URL + relativeUrl;
    }

}
