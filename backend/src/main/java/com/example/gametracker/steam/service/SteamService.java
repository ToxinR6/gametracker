package com.example.gametracker.steam.service;

import com.example.gametracker.steam.dto.ResolveVanityResponse;
import com.example.gametracker.steam.dto.SteamGameDTO;
import com.example.gametracker.steam.dto.SteamOwnedGamesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SteamService {

    private final RestTemplate restTemplate;

    @Value("${steam.api.key}")
    private String apiKey;

    @Value("${steam.api.owned-games-url}")
    private String ownedGamesUrl;

    public String resolveSteamId(String input) {

        if (input.contains("/profiles/")) {
            return input.replaceAll(".*/profiles/(\\d+).*", "$1");
        }

        String vanityName = input;

        if (input.contains("/id/")) {
            vanityName = input.replaceAll(".*/id/([^/]+).*", "$1");
        }

        String url =
                "https://api.steampowered.com/ISteamUser/ResolveVanityURL/v0001/"
                        + "?key=" + apiKey
                        + "&vanityurl=" + vanityName;

        ResolveVanityResponse response =
                restTemplate.getForObject(url, ResolveVanityResponse.class);

        if (response == null || response.getResponse() == null ||
                response.getResponse().getSteamid() == null) {
            throw new IllegalArgumentException("Invalid Steam profile");
        }

        return response.getResponse().getSteamid();
    }

    public List<SteamGameDTO> getOwnedGames(String steamId) {

        String url = ownedGamesUrl
                + "?key=" + apiKey
                + "&steamid=" + steamId
                + "&include_appinfo=true";

        SteamOwnedGamesResponse response =
                restTemplate.getForObject(url, SteamOwnedGamesResponse.class);

        if (response == null || response.getResponse() == null) {
            return List.of();
        }

        return response.getResponse().getGames();
    }

    public SteamGameDTO getGameByAppId(String steamId, long appId) {
        return getOwnedGames(steamId).stream()
                .filter(game -> game.getAppId() == appId)
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("Game not found in Steam library")
                );
    }

    public int convertMinutesToHours(int minutes) {
        return minutes / 60;
    }
}
