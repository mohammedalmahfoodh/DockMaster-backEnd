package com.kockumation.backEnd.dockMaster.model;

public class PontoonCurrentCoordinate {

    private int id;
    private float xCoordinate ;
    private float currentDraft;
    private String shipSide;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(float xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public float getCurrentDraft() {
        return currentDraft;
    }

    public void setCurrentDraft(float currentDraft) {
        this.currentDraft = currentDraft;
    }

    public String getShipSide() {
        return shipSide;
    }

    public void setShipSide(String shipSide) {
        this.shipSide = shipSide;
    }

    @Override
    public String toString() {
        return "PontoonCurrentCoordinate{" +
                "id=" + id +
                ", xCoordinate=" + xCoordinate +
                ", currentDraft=" + currentDraft +
                ", shipSide='" + shipSide + '\'' +
                '}';
    }
}
