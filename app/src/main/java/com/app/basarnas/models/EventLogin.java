package com.app.basarnas.models;

/**
 * Created by luthfi on 24/04/2017.
 */

public class EventLogin {
    private String type;

    public EventLogin(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
