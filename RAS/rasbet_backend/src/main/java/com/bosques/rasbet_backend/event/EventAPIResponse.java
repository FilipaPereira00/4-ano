package com.bosques.rasbet_backend.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventAPIResponse {
    @JsonIgnore
    private int code;

    @JsonIgnore
    private String message;

    private List<Event> data;

    public EventAPIResponse()
    {

    }

    public EventAPIResponse(ArrayList<Event> data) {
        this.data = data;
    }

    public List<Event> getData() {
        return data;
    }

    public void setData(ArrayList<Event> data) {
        this.data = data;
    }
}
