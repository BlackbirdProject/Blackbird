package com.madblackbird.blackbird.dataClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TimeEstimate {

    public TimeEstimate() {

    }

    @SerializedName("localized_display_name")
    @Expose
    private String localizedisplayName;

    @SerializedName("display_name")
    @Expose
    private String displayName;

    @SerializedName("product_id")
    @Expose
    private String productid;

    @SerializedName("estimate")
    @Expose
    private int estimate;

    public String getLocalizedisplayName() {
        return localizedisplayName;
    }

    public void setLocalizedisplayName(String localizedisplayName) {
        this.localizedisplayName = localizedisplayName;
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

    public int getEstimate() {
        return estimate;
    }

    public void setEstimate(int estimate) {
        this.estimate = estimate;
    }

}
