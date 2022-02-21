package com.bosques.rasbet_backend.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserAPIResponse {

    private List<User> results;

    public UserAPIResponse() {

    }

    public UserAPIResponse(List<User> results) {
        this.results = results;
    }

    public void setResults(List<User> results) {
        this.results = results;
    }

    public List<User> getResults() {
        return results;
    }
}
