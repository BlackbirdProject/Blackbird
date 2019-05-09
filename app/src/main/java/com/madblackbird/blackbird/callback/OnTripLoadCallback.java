package com.madblackbird.blackbird.callback;

import com.madblackbird.blackbird.dataClasses.Plan;

public interface OnTripLoadCallback {

    void onItineraryLoaded(Plan plan);

    void onLoadError();

}
