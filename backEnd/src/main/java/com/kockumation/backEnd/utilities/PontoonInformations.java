package com.kockumation.backEnd.utilities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class PontoonInformations {
  private float ruleLength;
  private float maxPontoonDive;
    List<PontoonInfo>pontoonConfig;

    public float getRuleLength() {
        return ruleLength;
    }

    public void setRuleLength(float ruleLength) {
        this.ruleLength = ruleLength;
    }

    public float getMaxPontoonDive() {
        return maxPontoonDive;
    }

    public void setMaxPontoonDive(float maxPontoonDive) {
        this.maxPontoonDive = maxPontoonDive;
    }

    public List<PontoonInfo> getPontoonConfig() {
        return pontoonConfig;
    }

    public void setPontoonConfig(List<PontoonInfo> pontoonConfig) {
        this.pontoonConfig = pontoonConfig;
    }
}
