package com.utshelps.utshelpsapp;


public class Session {

    private String Availability;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    private String Location;
    private String SessionCode;
    private String Time;
    private String Date;
    private int Slot;
    private String Topic;
    private String attendanceRecorded;
    private String Type;

    public Session(String availability, String date, String location, String sessionCode, int slot, String time, String topic, String attendanceRecorded, String type) {
        this.Availability = availability;
        this.Location = location;
        this.SessionCode = sessionCode;
        this.Slot = slot;
        this.Time = time;
        this.Date = date;
        this.Topic = topic;
        this.attendanceRecorded = attendanceRecorded;
        this.Type = type;
    }

    public Session() {

    }

    public String getAvailability() {
        return Availability;
    }

    public void setAvailability(String availability) {
        Availability = availability;
    }


    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getSessionCode() {
        return SessionCode;
    }

    public void setSessionCode(String sessionCode) {
        SessionCode = sessionCode;
    }

    public int getSlot() {
        return Slot;
    }

    public void setSlot(int slot) {
        Slot = slot;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }


    public String getTopic() {
        return Topic;
    }

    public void setTopic(String topic) {
        Topic = topic;
    }

    public String getAttendanceRecorded() {
        return attendanceRecorded;
    }

    public void setAttendanceRecorded(String attendanceRecorded) {
        this.attendanceRecorded = attendanceRecorded;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}