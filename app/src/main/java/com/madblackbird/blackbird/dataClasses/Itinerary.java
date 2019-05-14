package com.madblackbird.blackbird.dataClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Itinerary implements Serializable {

    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("startTime")
    @Expose
    private Long startTime;
    @SerializedName("endTime")
    @Expose
    private Long endTime;
    @SerializedName("walkTime")
    @Expose
    private Integer walkTime;
    @SerializedName("transitTime")
    @Expose
    private Integer transitTime;
    @SerializedName("waitingTime")
    @Expose
    private Integer waitingTime;
    @SerializedName("walkDistance")
    @Expose
    private Double walkDistance;
    @SerializedName("walkLimitExceeded")
    @Expose
    private Boolean walkLimitExceeded;
    @SerializedName("elevationLost")
    @Expose
    private Integer elevationLost;
    @SerializedName("elevationGained")
    @Expose
    private Integer elevationGained;
    @SerializedName("transfers")
    @Expose
    private Integer transfers;
    @SerializedName("legs")
    @Expose
    private List<Leg> legs = null;
    @SerializedName("tooSloped")
    @Expose
    private Boolean tooSloped;

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Integer getWalkTime() {
        return walkTime;
    }

    public void setWalkTime(Integer walkTime) {
        this.walkTime = walkTime;
    }

    public Integer getTransitTime() {
        return transitTime;
    }

    public void setTransitTime(Integer transitTime) {
        this.transitTime = transitTime;
    }

    public Integer getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(Integer waitingTime) {
        this.waitingTime = waitingTime;
    }

    public Double getWalkDistance() {
        return walkDistance;
    }

    public void setWalkDistance(Double walkDistance) {
        this.walkDistance = walkDistance;
    }

    public Boolean getWalkLimitExceeded() {
        return walkLimitExceeded;
    }

    public void setWalkLimitExceeded(Boolean walkLimitExceeded) {
        this.walkLimitExceeded = walkLimitExceeded;
    }

    public Integer getElevationLost() {
        return elevationLost;
    }

    public void setElevationLost(Integer elevationLost) {
        this.elevationLost = elevationLost;
    }

    public Integer getElevationGained() {
        return elevationGained;
    }

    public void setElevationGained(Integer elevationGained) {
        this.elevationGained = elevationGained;
    }

    public Integer getTransfers() {
        return transfers;
    }

    public void setTransfers(Integer transfers) {
        this.transfers = transfers;
    }

    public List<Leg> getLegs() {
        return legs;
    }

    public void setLegs(List<Leg> legs) {
        this.legs = legs;
    }

    public Boolean getTooSloped() {
        return tooSloped;
    }

    public void setTooSloped(Boolean tooSloped) {
        this.tooSloped = tooSloped;
    }

}
