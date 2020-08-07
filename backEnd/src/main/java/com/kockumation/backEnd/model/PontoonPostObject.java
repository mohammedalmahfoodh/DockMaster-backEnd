package com.kockumation.backEnd.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
@JsonIgnoreProperties(ignoreUnknown = true)
public class PontoonPostObject {

    @NotNull
    @Min(1)
    private int pontoon_id;

    public int getPontoon_id() {
        return pontoon_id;
    }

    public void setPontoon_id(int pontoon_id) {
        this.pontoon_id = pontoon_id;
    }
}
