package com.kockumation.backEnd.dockMaster.model;

public class PontoonRefCoordinate {
    private int id;
    private float xCoordinate ;
    private float refDraft;
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
        return "PontoonRefCoordinate{" +
                "id=" + id +
                ", xCoordinate=" + xCoordinate +
                ", refDraft=" + refDraft +
                ", shipSide='" + shipSide + '\'' +
                '}';
    }
}
