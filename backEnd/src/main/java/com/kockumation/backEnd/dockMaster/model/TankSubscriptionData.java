package com.kockumation.backEnd.dockMaster.model;

import com.kockumation.backEnd.dockMaster.model.TankAlarmData;

import java.util.List;

public class TankSubscriptionData {
    private List<TankAlarmData> setTankSubscriptionData;

    public List<TankAlarmData> getSetTankSubscriptionData() {
        return setTankSubscriptionData;
    }

    public void setSetTankSubscriptionData(List<TankAlarmData> setTankSubscriptionData) {
        this.setTankSubscriptionData = setTankSubscriptionData;
    }

}
