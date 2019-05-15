package com.madblackbird.blackbird.dataClasses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

public class Plan implements Serializable {


    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("from")
    @Expose
    private OTPPlace from;

    @SerializedName("to")
    @Expose
    private OTPPlace to;

    @SerializedName("itineraries")
    @Expose
    private List<Itinerary> itineraries;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public List<Itinerary> getItineraries() {
        return itineraries;
    }

    public void setItineraries(List<Itinerary> itineraries) {
        this.itineraries = itineraries;
    }

    @NotNull
    @Override
    public String toString() {
        return "ClassPojo [date = " + date + ", from = " + from + ", to = " + to + "]";
    }

}
