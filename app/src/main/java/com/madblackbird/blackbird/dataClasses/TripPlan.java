package com.madblackbird.blackbird.dataClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TripPlan {

    @SerializedName("plan")
    @Expose
    private Plan plan;

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

}
