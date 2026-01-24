package com.example.gametracker.steam.service;

import com.example.gametracker.steam.dto.SteamGameDTO;
import com.example.gametracker.steam.dto.SteamOwnedGamesResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class SteamService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${steam.api.key}")
    private String apiKey;

    @Value("${steam.api.url}")
    private String steamApiUrl;

    public List<SteamGameDTO> getOwnedGames(String steamId) {

        String url = steamApiUrl
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
    public SteamGameDTO getGameByAppId(String steamId, long appId){

        List<SteamGameDTO> games = getOwnedGames(steamId);

        return games.stream()
                .filter(game -> game.getAppId() == appId)
                .findFirst()
                .orElseThrow(()->
                        new RuntimeException("Game not found in Steam library")
                );
    }



}
