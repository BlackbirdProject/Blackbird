package com.madblackbird.blackbird.callback;

import com.madblackbird.blackbird.dataClasses.PriceEstimates;

public interface OnPriceEstimatesLoadCallback {

    void onLoad(PriceEstimates priceEstimates);

    void onLoadError();

}
