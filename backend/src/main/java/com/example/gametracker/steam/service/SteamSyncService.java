package com.example.gametracker.steam.service;

import com.example.gametracker.entity.Game;
import com.example.gametracker.entity.GameSource;
import com.example.gametracker.entity.GameStatus;
import com.example.gametracker.repository.GameRepository;
import com.example.gametracker.steam.dto.SteamGameDTO;
import com.example.gametracker.steam.dto.SteamSyncResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SteamSyncService {

    private final SteamService steamService;
    private final GameRepository gameRepository;

    public SteamSyncResponse syncOwnedGames(String steamInput) {

        String steamId = steamService.resolveSteamId(steamInput);
        List<SteamGameDTO> steamGames = steamService.getOwnedGames(steamId);

        int createdCount = 0;
        int updatedCount = 0;
        int sampleLimit = 200;
        List<Long> sampleAppIds = new ArrayList<>();
        List<String> sampleTitles = new ArrayList<>();

        for (SteamGameDTO steamGame : steamGames) {
            boolean created = upsertGame(steamGame);
            if (created) {
                createdCount++;
            } else {
                updatedCount++;
            }

            if (sampleAppIds.size() < sampleLimit) {
                sampleAppIds.add(steamGame.getAppId());
            }
            if (sampleTitles.size() < sampleLimit) {
                sampleTitles.add(steamGame.getName());
            }
        }

        return SteamSyncResponse.builder()
                .steamInput(steamInput)
                .steamId(steamId)
                .totalGames(steamGames.size())
                .createdCount(createdCount)
                .updatedCount(updatedCount)
                .sampleAppIds(sampleAppIds)
                .sampleTitles(sampleTitles)
                .build();
    }

    private boolean upsertGame(SteamGameDTO steamGame) {

        Game game = gameRepository
                .findBySteamAppId(steamGame.getAppId())
                .orElseGet(() ->
                        gameRepository.findByTitleIgnoreCase(steamGame.getName())
                                .orElse(new Game())
                );

        boolean isNew = game.getId() == null;

        game.setTitle(steamGame.getName());
        game.setSteamAppId(steamGame.getAppId());
        game.setSource(GameSource.STEAM);
        game.setHoursPlayed(
                steamService.convertMinutesToHours(
                        steamGame.getPlaytimeForever()
                )
        );

        if (game.getStatus() == null) {
            game.setStatus(
                    game.getHoursPlayed() > 0
                            ? GameStatus.PLAYING
                            : GameStatus.BACKLOG
            );
        }

        game.setImageUrl(
                "https://cdn.cloudflare.steamstatic.com/steam/apps/"
                        + steamGame.getAppId()
                        + "/header.jpg"
        );

        gameRepository.save(game);
        return isNew;
    }
}
