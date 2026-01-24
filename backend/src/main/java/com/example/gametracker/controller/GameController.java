package com.example.gametracker.controller;


import com.example.gametracker.dto.GameRequestDTO;
import com.example.gametracker.dto.GameResponseDTO;
import com.example.gametracker.entity.Game;
import com.example.gametracker.service.GameService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
@CrossOrigin(origins = "http://localhost:5173")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    // CREATE
    @PostMapping
    public GameResponseDTO createGame(
            @Valid @RequestBody GameRequestDTO dto
    ) {
        return gameService.createGame(dto);
    }

    // READ ALL
    @GetMapping
    public List<GameResponseDTO> getAllGames() {
        return gameService.getAllGames();
    }

    // READ BY ID
    @GetMapping("/{id}")
    public GameResponseDTO getGameById(@PathVariable Long id) {
        return gameService.getGameById(id);
    }

    // UPDATE
    @PutMapping("/{id}")
    public GameResponseDTO updateGame(
            @PathVariable Long id,
            @Valid @RequestBody GameRequestDTO dto
    ) {
        return gameService.updateGame(id, dto);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteGame(@PathVariable Long id) {
        gameService.deleteGame(id);
    }
}

