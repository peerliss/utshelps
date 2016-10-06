package com.utshelps.utshelpsapp;

public class Session {

    String Availability;
    String Date;
    String Location;
    String SessionCode;
    String Time;
    int Slot;
    String Topic;
    String attendanceRecorded;


    public Session(String availability, String date, String location, String sessionCode, int slot, String time, String topic, String attendanceRecorded) {
        this.Availability = availability;
        this.Date = date;
        this.Location = location;
        this.SessionCode = sessionCode;
        this.Slot = slot;
        this.Time = time;
        this.Topic = topic;
        this.attendanceRecorded = attendanceRecorded;
    }

    public Session() {

    }

    public String getAvailability() {
        return Availability;
    }

    public void setAvailability(String availability) {
        Availability = availability;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
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
}