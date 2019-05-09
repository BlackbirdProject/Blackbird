package com.madblackbird.blackbird.dataClasses;

import org.jetbrains.annotations.NotNull;

public class OTPPlace {
    private String vertexType;

    private String orig;

    private String name;

    private String lon;

    private String lat;

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

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    @NotNull
    @Override
    public String toString() {
        return "ClassPojo [vertexType = " + vertexType + ", orig = " + orig + ", name = " + name + ", lon = " + lon + ", lat = " + lat + "]";
    }
}