package com.madblackbird.blackbird.dataClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TimeEstimates {

    public TimeEstimates() {

    }

    @SerializedName("times")
    @Expose
    private List<TimeEstimate> timeEstimates;

    public List<TimeEstimate> getTimeEstimates() {
        return timeEstimates;
    }

    public void setTimeEstimates(List<TimeEstimate> timeEstimates) {
        this.timeEstimates = timeEstimates;
    }

}
