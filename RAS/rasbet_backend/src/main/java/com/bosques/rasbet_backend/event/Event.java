package com.bosques.rasbet_backend.event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {


    private String recordId;
    private String matchId;
    private int matchTime;
    private int homeScore;
    private int awayScore;
    private int homeRedCount;
    private int awayRedCount;
    private int type;
    private int companyId;
    private String odds1, odds2, odds3;
    private int changeTime;

    public Event() {

    }

    public Event(String recordId, String matchId, int matchTime, int homeScore, int awayScore, int homeRedCount, int awayRedCount, int type, int companyId, String odds1, String odds2, String odds3, int changeTime) {
        this.recordId = recordId;
        this.matchId = matchId;
        this.matchTime = matchTime;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.homeRedCount = homeRedCount;
        this.awayRedCount = awayRedCount;
        this.type = type;
        this.companyId = companyId;
        this.odds1 = odds1;
        this.odds2 = odds2;
        this.odds3 = odds3;
        this.changeTime = changeTime;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public int getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(int matchTime) {
        this.matchTime = matchTime;
    }

    public int getHomeScore() {
        return homeScore;
    }

    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }

    public int getHomeRedCount() {
        return homeRedCount;
    }

    public void setHomeRedCount(int homeRedCount) {
        this.homeRedCount = homeRedCount;
    }

    public int getAwayRedCount() {
        return awayRedCount;
    }

    public void setAwayRedCount(int awayRedCount) {
        this.awayRedCount = awayRedCount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getOdds1() {
        return odds1;
    }

    public void setOdds1(String odds1) {
        this.odds1 = odds1;
    }

    public String getOdds2() {
        return odds2;
    }

    public void setOdds2(String odds2) {
        this.odds2 = odds2;
    }

    public String getOdds3() {
        return odds3;
    }

    public void setOdds3(String odds3) {
        this.odds3 = odds3;
    }

    public int getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(int changeTime) {
        this.changeTime = changeTime;
    }

    @Override
    public String toString() {
        return "Event{" +
                "recordId='" + recordId + '\'' +
                ", matchId='" + matchId + '\'' +
                ", matchTime=" + matchTime +
                ", homeScore=" + homeScore +
                ", awayScore=" + awayScore +
                ", homeRedCount=" + homeRedCount +
                ", awayRedCount=" + awayRedCount +
                ", type=" + type +
                ", companyId=" + companyId +
                ", odds1='" + odds1 + '\'' +
                ", odds2='" + odds2 + '\'' +
                ", odds3='" + odds3 + '\'' +
                ", changeTime=" + changeTime +
                '}';
    }
}
