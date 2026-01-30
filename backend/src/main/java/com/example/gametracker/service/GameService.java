package com.example.gametracker.service;

import com.example.gametracker.dto.GameRequestDTO;
import com.example.gametracker.dto.GameResponseDTO;
import com.example.gametracker.entity.Game;
import com.example.gametracker.entity.GameStatus;
import com.example.gametracker.exception.GameNotFoundException;
import com.example.gametracker.repository.GameRepository;
import com.example.gametracker.steam.dto.SteamGameDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    // CREATE
    public GameResponseDTO createGame(GameRequestDTO dto) {
        if (gameRepository.existsByTitleIgnoreCase(dto.getTitle())){
            throw new RuntimeException("Game already exists");
        }
        Game game = mapToEntity(dto);
        Game savedGame = gameRepository.save(game);
        return mapToResponse(savedGame);
    }

    // READ ALL
    public List<GameResponseDTO> getAllGames() {
        return gameRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public GameResponseDTO updateStatus(Long id, String status) {

        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new GameNotFoundException(id));

        game.setStatus(GameStatus.valueOf(status));

        Game saved = gameRepository.save(game);
        return mapToResponse(saved);
    }


    // READ BY ID
    public GameResponseDTO getGameById(Long id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new GameNotFoundException(id));
        return mapToResponse(game);
    }

    // UPDATE
    public GameResponseDTO updateGame(Long id, GameRequestDTO dto) {
        Game existingGame = gameRepository.findById(id)
                .orElseThrow(() -> new GameNotFoundException(id));

        existingGame.setTitle(dto.getTitle());
        existingGame.setStatus(dto.getStatus());
        existingGame.setHoursPlayed(dto.getHoursPlayed());
        existingGame.setNotes(dto.getNotes());

        Game updatedGame = gameRepository.save(existingGame);
        return mapToResponse(updatedGame);
    }

    // DELETE
    public void deleteGame(Long id) {
        if (!gameRepository.existsById(id)) {
            throw new RuntimeException("Game not found");
        }
        gameRepository.deleteById(id);
    }

    // -------- MAPPING --------

    private Game mapToEntity(GameRequestDTO dto) {
        Game game = new Game();
        game.setTitle(dto.getTitle());
        game.setStatus(dto.getStatus());
        game.setHoursPlayed(dto.getHoursPlayed());
        game.setNotes(dto.getNotes());
        return game;
    }

    private GameResponseDTO mapToResponse(Game game) {
        GameResponseDTO dto = new GameResponseDTO();
        dto.setId(game.getId());
        dto.setTitle(game.getTitle());
        dto.setStatus(game.getStatus().name());
        dto.setHoursPlayed(game.getHoursPlayed());
        dto.setNotes(game.getNotes());
        return dto;
    }

    public GameResponseDTO importFromSteam(SteamGameDTO steamGame){

        if(gameRepository.existsBySteamAppId(steamGame.getAppId())){
            throw new RuntimeException("Game already imported from Steam");
        }
        if (gameRepository.existsByTitleIgnoreCase(steamGame.getName())) {
            throw new RuntimeException("Game already exists (same title)");
        }

        Game game = new Game();
        game.setSteamAppId(steamGame.getAppId());
        game.setTitle(steamGame.getName());
        game.setHoursPlayed(steamGame.getPlaytimeForever());
        game.setStatus(GameStatus.PLAYING);
        game.setNotes("Imported from Steam");

        Game saved =  gameRepository.save(game);
        return mapToResponse(saved);

    }
}
