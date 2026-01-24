package com.example.gametracker.steam.dto;

import java.util.List;

public class SteamOwnedGamesResponse {

    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public static class Response {

        private List<SteamGameDTO> games;

        public List<SteamGameDTO> getGames() {
            return games;
        }

        public void setGames(List<SteamGameDTO> games) {
            this.games = games;
        }
    }
}
