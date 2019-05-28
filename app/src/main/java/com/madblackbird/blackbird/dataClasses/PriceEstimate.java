package com.madblackbird.blackbird.dataClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PriceEstimate {

    @SerializedName("localized_display_name")
    @Expose
    private String localizedisplayName;

    @SerializedName("distance")
    @Expose
    private Double distance;

    @SerializedName("display_name")
    @Expose
    private String displayName;

    @SerializedName("product_id")
    @Expose
    private String productid;

    @SerializedName("high_estimate")
    @Expose
    private Double highEstimate;

    @SerializedName("low_estimate")
    @Expose
    private Double lowEstimate;

    @SerializedName("duration")
    @Expose
    private Integer duration;

    @SerializedName("estimate")
    @Expose
    private String estimate;

    @SerializedName("currency_code")
    @Expose
    private String currencyCode;

    public String getLocalizedisplayName() {
        return localizedisplayName;
    }

    public void setLocalizedisplayName(String localizedisplayName) {
        this.localizedisplayName = localizedisplayName;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public Double getHighEstimate() {
        return highEstimate;
    }

    public void setHighEstimate(Double highEstimate) {
        this.highEstimate = highEstimate;
    }

    public Double getLowEstimate() {
        return lowEstimate;
    }

    public void setLowEstimate(Double lowEstimate) {
        this.lowEstimate = lowEstimate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getEstimate() {
        return estimate;
    }

    public void setEstimate(String estimate) {
        this.estimate = estimate;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

}
