package com.kockumation.backEnd.model;

import java.util.ArrayList;
import java.util.List;

public class Heeling {

    private  float heeling;
    List<DeltaDraft>deltaDrafts = new ArrayList<>();

    public float getHeeling() {
        return heeling;
    }

    public void setHeeling(float heeling) {
        this.heeling = heeling;
    }

    public List<DeltaDraft> getDeltaDrafts() {
        return deltaDrafts;
    }

    public void setDeltaDrafts(List<DeltaDraft> deltaDrafts) {
        this.deltaDrafts = deltaDrafts;
    }
}
