package com.example.gametracker.steam.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResolveVanityResponse {

    private VanityResponse response;

    @Getter
    @Setter
    public static class VanityResponse {
        private String steamid;
    }
}
