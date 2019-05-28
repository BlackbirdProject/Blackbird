package com.madblackbird.blackbird.dataClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PriceEstimates {

    @SerializedName("prices")
    @Expose
    private List<PriceEstimate> priceEstimates;

    public List<PriceEstimate> getPriceEstimates() {
        return priceEstimates;
    }

    public void setPriceEstimates(List<PriceEstimate> priceEstimates) {
        this.priceEstimates = priceEstimates;
    }

}
