package com.madblackbird.blackbird.callback;

import com.madblackbird.blackbird.dataClasses.Itinerary;

public interface OnTripLoadCallback {

    void onItineraryLoaded(Itinerary itinerary);

    void onLoadError();

}
