package com.example.gametracker.steam.service;

import com.example.gametracker.entity.Game;
import com.example.gametracker.entity.GameSource;
import com.example.gametracker.entity.GameStatus;
import com.example.gametracker.repository.GameRepository;
import com.example.gametracker.steam.dto.SteamGameDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SteamSyncService {

    private final SteamService steamService;
    private final GameRepository gameRepository;

    public void syncOwnedGames(String steamInput) {

        String steamId = steamService.resolveSteamId(steamInput);
        List<SteamGameDTO> steamGames = steamService.getOwnedGames(steamId);

        for (SteamGameDTO steamGame : steamGames) {
            upsertGame(steamGame);
        }
    }

    private void upsertGame(SteamGameDTO steamGame) {

        Game game = gameRepository
                .findBySteamAppId(steamGame.getAppId())
                .orElseGet(Game::new);

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
    }
}
