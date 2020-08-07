package com.kockumation.backEnd.utilities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PontoonInfo {
    private int id;
    private float xCoordinate ;
    private float deflectionLimit;
    private float offset;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getOffset() {
        return offset;
    }

    public void setOffset(float offset) {
        this.offset = offset;
    }

    public float getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(float xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public float getDeflectionLimit() {
        return deflectionLimit;
    }

    public void setDeflectionLimit(float deflectionLimit) {
        this.deflectionLimit = deflectionLimit;
    }

    @Override
    public String toString() {
        return "PontoonInfo{" +
                "id=" + id +
                ", xCoordinate=" + xCoordinate +
                ", deflectionLimit=" + deflectionLimit +
                '}';
    }
}
