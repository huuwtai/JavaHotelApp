package com.example.bookingapp.Database;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Room implements Serializable {
    String roomID;
    String roomName;
    String rtid;
    String roomAvatar;
    String rtname;

    //empty constructor
    public Room(){}
    //constructor

    public Room(String roomID, String roomName, String rtid, String roomAvatar, String rtname) {
        this.roomID = roomID;
        this.roomName = roomName;
        this.rtid = rtid;
        this.roomAvatar = roomAvatar;
        this.rtname = rtname;
    }

    //getter and setter

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getRtid() {
        return rtid;
    }

    public void setRtid(String rtid) {
        this.rtid = rtid;
    }

    public String getRoomAvatar() {
        return roomAvatar;
    }

    public void setRoomAvatar(String roomAvatar) {
        this.roomAvatar = roomAvatar;
    }

    public String getRtname() {
        return rtname;
    }

    public void setRtname(String rtname) {
        this.rtname = rtname;
    }

    public Map<String,Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("roomName",roomName);
        return result;
    }

}
