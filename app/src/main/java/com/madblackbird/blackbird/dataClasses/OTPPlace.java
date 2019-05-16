package com.madblackbird.blackbird.dataClasses;

import com.google.android.gms.maps.model.LatLng;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class OTPPlace implements Serializable {

    private String vertexType;

    private String orig;

    private String name;

    private Double lon;

    private Double lat;

    public OTPPlace(LatLng latLng) {
        lat = latLng.latitude;
        lon = latLng.longitude;
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

    public String getOrig() {
        return orig;
    }

    public void setOrig(String orig) {
        this.orig = orig;
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
        return "ClassPojo [vertexType = " + vertexType + ", orig = " + orig + ", name = " + name + ", lon = " + lon + ", lat = " + lat + "]";
    }
}