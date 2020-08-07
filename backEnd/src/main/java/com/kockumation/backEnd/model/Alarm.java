package com.kockumation.backEnd.model;

public class Alarm {

    private int alarm_id;
    private int pontoon_id;
    private String alarm_name;
    private  String alarm_date;
    private int acknowledged;
    private String alarm_description;
    private int alarm_active;
    private  int alarm_state;
    private String time_accepted;
    private String time_retrieved;

    public int getAlarm_id() {
        return alarm_id;
    }

    public void setAlarm_id(int alarm_id) {
        this.alarm_id = alarm_id;
    }

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

    public String getAlarm_date() {
        return alarm_date;
    }

    public void setAlarm_date(String alarm_date) {
        this.alarm_date = alarm_date;
    }

    public int getAcknowledged() {
        return acknowledged;
    }

    public void setAcknowledged(int acknowledged) {
        this.acknowledged = acknowledged;
    }

    public String getAlarm_description() {
        return alarm_description;
    }

    public void setAlarm_description(String alarm_description) {
        this.alarm_description = alarm_description;
    }

    public int getAlarm_active() {
        return alarm_active;
    }

    public void setAlarm_active(int alarm_active) {
        this.alarm_active = alarm_active;
    }

    public int getAlarm_state() {
        return alarm_state;
    }

    public void setAlarm_state(int alarm_state) {
        this.alarm_state = alarm_state;
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

    @Override
    public String toString() {
        return "Alarm{" +
                "alarm_id=" + alarm_id +
                ", pontoon_id=" + pontoon_id +
                ", alarm_name='" + alarm_name + '\'' +
                ", alarm_date='" + alarm_date + '\'' +
                ", acknowledged=" + acknowledged +
                ", alarm_description='" + alarm_description + '\'' +
                ", alarm_active=" + alarm_active +
                ", alarm_state=" + alarm_state +
                ", time_accepted='" + time_accepted + '\'' +
                ", time_retrieved='" + time_retrieved + '\'' +
                '}';
    }
}
