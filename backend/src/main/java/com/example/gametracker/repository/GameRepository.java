package com.example.gametracker.repository;

import com.example.gametracker.entity.Game;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, Long> {
    boolean existsBySteamAppId(Long steamAppId);

    Optional<Game> findBySteamAppId(Long steamAppId);
    boolean existsByTitleIgnoreCase(@NotBlank String title);
}
