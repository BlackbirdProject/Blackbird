package com.madblackbird.blackbird.dataClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Leg implements Serializable {

    public Leg() {

    }

    @SerializedName("startTime")
    @Expose
    private Double startTime;
    @SerializedName("endTime")
    @Expose
    private Double endTime;
    @SerializedName("departureDelay")
    @Expose
    private Integer departureDelay;
    @SerializedName("arrivalDelay")
    @Expose
    private Integer arrivalDelay;
    @SerializedName("realTime")
    @Expose
    private Boolean realTime;
    @SerializedName("distance")
    @Expose
    private Double distance;
    @SerializedName("pathway")
    @Expose
    private Boolean pathway;
    @SerializedName("mode")
    @Expose
    private String mode;
    @SerializedName("route")
    @Expose
    private String route;
    @SerializedName("agencyTimeZoneOffset")
    @Expose
    private Integer agencyTimeZoneOffset;
    @SerializedName("interlineWithPreviousLeg")
    @Expose
    private Boolean interlineWithPreviousLeg;
    @SerializedName("rentedBike")
    @Expose
    private Boolean rentedBike;
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("transitLeg")
    @Expose
    private Boolean transitLeg;
    @SerializedName("agencyName")
    @Expose
    private String agencyName;
    @SerializedName("agencyUrl")
    @Expose
    private String agencyUrl;
    @SerializedName("routeColor")
    @Expose
    private String routeColor;
    @SerializedName("routeType")
    @Expose
    private Integer routeType;
    @SerializedName("routeId")
    @Expose
    private String routeId;
    @SerializedName("routeTextColor")
    @Expose
    private String routeTextColor;
    @SerializedName("tripShortName")
    @Expose
    private String tripShortName;
    @SerializedName("headsign")
    @Expose
    private String headsign;
    @SerializedName("agencyId")
    @Expose
    private String agencyId;
    @SerializedName("tripId")
    @Expose
    private String tripId;
    @SerializedName("serviceDate")
    @Expose
    private String serviceDate;
    @SerializedName("routeShortName")
    @Expose
    private String routeShortName;
    @SerializedName("routeLongName")
    @Expose
    private String routeLongName;
    @SerializedName("legGeometry")
    @Expose
    private LegGeometry legGeometry;
    @SerializedName("from")
    @Expose
    private OTPPlace from;
    @SerializedName("intermediateStops")
    @Expose
    private List<Stop> intermediateStops;

    @SerializedName("to")
    @Expose
    private OTPPlace to;

    private boolean expanded;

    public Double getStartTime() {
        return startTime;
    }

    public void setStartTime(Double startTime) {
        this.startTime = startTime;
    }

    public Double getEndTime() {
        return endTime;
    }

    public void setEndTime(Double endTime) {
        this.endTime = endTime;
    }

    public Integer getDepartureDelay() {
        return departureDelay;
    }

    public void setDepartureDelay(Integer departureDelay) {
        this.departureDelay = departureDelay;
    }

    public Integer getArrivalDelay() {
        return arrivalDelay;
    }

    public void setArrivalDelay(Integer arrivalDelay) {
        this.arrivalDelay = arrivalDelay;
    }

    public Boolean getRealTime() {
        return realTime;
    }

    public void setRealTime(Boolean realTime) {
        this.realTime = realTime;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Boolean getPathway() {
        return pathway;
    }

    public void setPathway(Boolean pathway) {
        this.pathway = pathway;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public Integer getAgencyTimeZoneOffset() {
        return agencyTimeZoneOffset;
    }

    public void setAgencyTimeZoneOffset(Integer agencyTimeZoneOffset) {
        this.agencyTimeZoneOffset = agencyTimeZoneOffset;
    }

    public Boolean getInterlineWithPreviousLeg() {
        return interlineWithPreviousLeg;
    }

    public void setInterlineWithPreviousLeg(Boolean interlineWithPreviousLeg) {
        this.interlineWithPreviousLeg = interlineWithPreviousLeg;
    }

    public Boolean getRentedBike() {
        return rentedBike;
    }

    public void setRentedBike(Boolean rentedBike) {
        this.rentedBike = rentedBike;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Boolean getTransitLeg() {
        return transitLeg;
    }

    public void setTransitLeg(Boolean transitLeg) {
        this.transitLeg = transitLeg;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getAgencyUrl() {
        return agencyUrl;
    }

    public void setAgencyUrl(String agencyUrl) {
        this.agencyUrl = agencyUrl;
    }

    public String getRouteColor() {
        return routeColor;
    }

    public void setRouteColor(String routeColor) {
        this.routeColor = routeColor;
    }

    public Integer getRouteType() {
        return routeType;
    }

    public void setRouteType(Integer routeType) {
        this.routeType = routeType;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getRouteTextColor() {
        return routeTextColor;
    }

    public void setRouteTextColor(String routeTextColor) {
        this.routeTextColor = routeTextColor;
    }

    public String getTripShortName() {
        return tripShortName;
    }

    public void setTripShortName(String tripShortName) {
        this.tripShortName = tripShortName;
    }

    public String getHeadsign() {
        return headsign;
    }

    public void setHeadsign(String headsign) {
        this.headsign = headsign;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(String serviceDate) {
        this.serviceDate = serviceDate;
    }

    public String getRouteShortName() {
        return routeShortName;
    }

    public void setRouteShortName(String routeShortName) {
        this.routeShortName = routeShortName;
    }

    public String getRouteLongName() {
        return routeLongName;
    }

    public void setRouteLongName(String routeLongName) {
        this.routeLongName = routeLongName;
    }

    public LegGeometry getLegGeometry() {
        return legGeometry;
    }

    public void setLegGeometry(LegGeometry legGeometry) {
        this.legGeometry = legGeometry;
    }

    public OTPPlace getFrom() {
        return from;
    }

    public void setFrom(OTPPlace from) {
        this.from = from;
    }

    public OTPPlace getTo() {
        return to;
    }

    public void setTo(OTPPlace to) {
        this.to = to;
    }

    public List<Stop> getIntermediateStops() {
        return intermediateStops;
    }

    public void setIntermediateStops(List<Stop> intermediateStops) {
        this.intermediateStops = intermediateStops;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

}
