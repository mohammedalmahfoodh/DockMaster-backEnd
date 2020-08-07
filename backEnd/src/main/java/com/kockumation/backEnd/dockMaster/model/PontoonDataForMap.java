package com.kockumation.backEnd.dockMaster.model;

public class PontoonDataForMap {

    private int pontoon_id;
    private int alarm_state;
    private String alarm_date;
    private String time_accepted;
    private String time_retrieved;
    private boolean alarm_active;
    private String alarm_description;
    private boolean acknowledged;
    private String alarm_name;
    private boolean inserted;
    private boolean updateBlue;
    private boolean updateRed;
    private float xCoordinate ;
    private float deflectionLimit;
    private float offset;
    private float currentDraft;
    private float refDraft;
    private String shipSide;
    private float  draftDifference ;


    public float getDraftDifference() {
        return draftDifference;
    }

    public void setDraftDifference(float draftDifference) {
        this.draftDifference = draftDifference;
    }

    public String getShipSide() {
        return shipSide;
    }

    public void setShipSide(String shipSide) {
        this.shipSide = shipSide;
    }

    public PontoonDataForMap() {
        updateRed = true;
        updateBlue = true;
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

    public float getOffset() {
        return offset;
    }

    public void setOffset(float offset) {
        this.offset = offset;
    }

    public float getDeflectionLimit() {
        return deflectionLimit;
    }

    public void setDeflectionLimit(float deflectionLimit) {
        this.deflectionLimit = deflectionLimit;
    }

    public float getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(float xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public int getPontoon_id() {
        return pontoon_id;
    }

    public void setPontoon_id(int pontoon_id) {
        this.pontoon_id = pontoon_id;
    }

    public int getAlarm_state() {
        return alarm_state;
    }

    public void setAlarm_state(int alarm_state) {
        this.alarm_state = alarm_state;
    }

    public String getAlarm_date() {
        return alarm_date;
    }

    public void setAlarm_date(String alarm_date) {
        this.alarm_date = alarm_date;
    }

    public String getTime_accepted() {
        return time_accepted;
    }

    public void setTime_accepted(String time_accepted) {
        this.time_accepted = time_accepted;
    }

    public String getTime_retrieved() {
        return time_retrieved;
    }

    public void setTime_retrieved(String time_retrieved) {
        this.time_retrieved = time_retrieved;
    }

    public boolean isAlarm_active() {
        return alarm_active;
    }

    public void setAlarm_active(boolean alarm_active) {
        this.alarm_active = alarm_active;
    }

    public String getAlarm_description() {
        return alarm_description;
    }

    public void setAlarm_description(String alarm_description) {
        this.alarm_description = alarm_description;
    }

    public boolean isAcknowledged() {
        return acknowledged;
    }

    public void setAcknowledged(boolean acknowledged) {
        this.acknowledged = acknowledged;
    }

    public String getAlarm_name() {
        return alarm_name;
    }

    public void setAlarm_name(String alarm_name) {
        this.alarm_name = alarm_name;
    }

    public boolean isInserted() {
        return inserted;
    }

    public void setInserted(boolean inserted) {
        this.inserted = inserted;
    }

    public boolean isUpdateBlue() {
        return updateBlue;
    }

    public void setUpdateBlue(boolean updateBlue) {
        this.updateBlue = updateBlue;
    }

    public boolean isUpdateRed() {
        return updateRed;
    }

    public void setUpdateRed(boolean updateRed) {
        this.updateRed = updateRed;
    }

    @Override
    public String toString() {
        return "PontoonDataForMap{" +
                "pontoon_id=" + pontoon_id +
                ", alarm_state=" + alarm_state +
                ", alarm_date='" + alarm_date + '\'' +
                ", time_accepted='" + time_accepted + '\'' +
                ", time_retrieved='" + time_retrieved + '\'' +
                ", alarm_active=" + alarm_active +
                ", alarm_description='" + alarm_description + '\'' +
                ", acknowledged=" + acknowledged +
                ", alarm_name='" + alarm_name + '\'' +
                ", inserted=" + inserted +
                ", updateBlue=" + updateBlue +
                ", updateRed=" + updateRed +
                ", xCoordinate=" + xCoordinate +
                ", deflectionLimit=" + deflectionLimit +
                ", offset=" + offset +
                ", currentDraft=" + currentDraft +
                ", refDraft=" + refDraft +
                ", shipSide='" + shipSide + '\'' +
                ", draftDifference=" + draftDifference +
                '}';
    }
}
