package com.kockumation.backEnd.model;

public class DeltaDraft {
    private float deltaDraft;
    private String deltaDraftName;

    public String getDeltaDraftName() {
        return deltaDraftName;
    }

    public void setDeltaDraftName(String deltaDraftName) {
        this.deltaDraftName = deltaDraftName;
    }

    public float getDeltaDraft() {
        return deltaDraft;
    }

    public void setDeltaDraft(float deltaDraft) {
        this.deltaDraft = deltaDraft;
    }

    @Override
    public String toString() {
        return "DeltaDraft{" +
                "deltaDraft=" + deltaDraft +
                ", deltaDraftName='" + deltaDraftName + '\'' +
                '}';
    }
}
