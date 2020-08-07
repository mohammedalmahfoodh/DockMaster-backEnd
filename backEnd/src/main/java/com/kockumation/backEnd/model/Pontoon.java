package com.kockumation.backEnd.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Pontoon {

    private int pontoon_id;
    private String alarm_name;
    private float xCoordinate;
    private float deflectionLimit;
    private float offset;
    private float currentDraft;
    private float refDraft;
    private String shipSide;

    public int getPontoon_id() {
        return pontoon_id;
    }

    public void setPontoon_id(int pontoon_id) {
        this.pontoon_id = pontoon_id;
    }

    public String getAlarm_name() {
        return alarm_name;
    }

    public void setAlarm_name(String alarm_name) {
        this.alarm_name = alarm_name;
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

    public float getOffset() {
        return offset;
    }

    public void setOffset(float offset) {
        this.offset = offset;
    }

    public float getCurrentDraft() {
        return currentDraft;
    }

    public void setCurrentDraft(float currentDraft) {
        this.currentDraft = currentDraft;
    }

    public float getRefDraft() {
        return refDraft;
    }

    public void setRefDraft(float refDraft) {
        this.refDraft = refDraft;
    }

    public String getShipSide() {
        return shipSide;
    }

    public void setShipSide(String shipSide) {
        this.shipSide = shipSide;
    }


    @Override
    public String toString() {
        return "Pontoon{" +
                "pontoon_id=" + pontoon_id +
                ", alarm_name='" + alarm_name + '\'' +
                ", xCoordinate=" + xCoordinate +
                ", deflectionLimit=" + deflectionLimit +
                ", offset=" + offset +
                ", currentDraft=" + currentDraft +
                ", refDraft=" + refDraft +
                ", shipSide='" + shipSide + '\'' +
                '}';
    }
}
