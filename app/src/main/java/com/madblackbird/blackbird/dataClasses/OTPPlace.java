package com.madblackbird.blackbird.dataClasses;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class OTPPlace implements Serializable {

    public OTPPlace() {

    }

    @SerializedName("vertexType")
    @Expose
    private String vertexType;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("lon")
    @Expose
    private Double lon;

    @SerializedName("lat")
    @Expose
    private Double lat;

    private String addressName;

    public OTPPlace(String name, String addressName, LatLng latLng) {
        this.name = name;
        this.addressName = addressName;
        lat = latLng.latitude;
        lon = latLng.longitude;
    }

    public OTPPlace(String vertexType, String name, Double lon, Double lat) {
        this.vertexType = vertexType;
        this.name = name;
        this.lon = lon;
        this.lat = lat;
    }

    public LatLng getLatLng() {
        return new LatLng(lat, lon);
    }

    public String getVertexType() {
        return vertexType;
    }

    public void setVertexType(String vertexType) {
        this.vertexType = vertexType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    @NotNull
    @Override
    public String toString() {
        return "ClassPojo [vertexType = " + vertexType + ", name = " + name + ", lon = " + lon + ", lat = " + lat + "]";
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }
}