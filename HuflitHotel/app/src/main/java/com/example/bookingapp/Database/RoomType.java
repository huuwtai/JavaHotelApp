package com.example.bookingapp.Database;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RoomType implements Serializable {
    String id;
    String rtname;
    public RoomType() {

    }
    public RoomType(String id, String rtname) {
        this.id = id;
        this.rtname = rtname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRtname() {
        return rtname;
    }

    public void setRtname(String rtname) {
        this.rtname = rtname;
    }

    public Map<String,Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("rtname",rtname);
        return result;
    }

}
