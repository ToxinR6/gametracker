package com.example.gametracker.steam.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SteamGameDTO {

    @JsonProperty("appid")
    private long appId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("playtime_forever")
    private int playtimeForever;

    public long getAppId() {
        return appId;
    }

    public String getName() {
        return name;
    }

    public int getPlaytimeForever() {
        return playtimeForever;
    }
}
