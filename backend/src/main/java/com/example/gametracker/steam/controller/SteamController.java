package com.example.gametracker.steam.controller;

import com.example.gametracker.dto.GameResponseDTO;
import com.example.gametracker.service.GameService;
import com.example.gametracker.steam.dto.SteamGameDTO;
import com.example.gametracker.steam.dto.SteamSyncResponse;
import com.example.gametracker.steam.service.SteamService;
import com.example.gametracker.steam.service.SteamSyncService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/steam")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class SteamController {

    private final SteamSyncService steamSyncService;
    private final SteamService steamService;
    private final GameService gameService;

    @PostMapping("/sync")
    public ResponseEntity<SteamSyncResponse> syncSteamGames(@RequestParam String steamInput) {
        SteamSyncResponse response = steamSyncService.syncOwnedGames(steamInput);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/games/{steamId}")
    public List<SteamGameDTO> getOwnedGames(@PathVariable String steamId) {
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
