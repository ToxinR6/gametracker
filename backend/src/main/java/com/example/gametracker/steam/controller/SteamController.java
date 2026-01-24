package com.example.gametracker.steam.controller;

import com.example.gametracker.dto.GameResponseDTO;
import com.example.gametracker.service.GameService;
import com.example.gametracker.steam.dto.SteamGameDTO;
import com.example.gametracker.steam.service.SteamService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/steam")
@CrossOrigin(origins = "http:localhost:5173")
public class SteamController {


    private final SteamService steamService;
    private final GameService gameService;

    public SteamController(SteamService steamService,GameService gameService) {
        this.gameService = gameService;
        this.steamService = steamService;
    }


    @GetMapping("/games/{steamId}")
    public List<SteamGameDTO> getOwnedGames(
            @PathVariable String steamId
    ) {
        return steamService.getOwnedGames(steamId);
    }

    @PostMapping("/import/{steamId}/{appId}")
    public GameResponseDTO importGameFromSteam(
            @PathVariable String steamId,
            @PathVariable long appId
    ) {
        SteamGameDTO steamGame =
                steamService.getGameByAppId(steamId, appId);

        return gameService.importFromSteam(steamGame);
    }

}
