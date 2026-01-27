package com.example.gametracker.dto;

import com.example.gametracker.entity.GameStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class GameRequestDTO {

    @NotBlank
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GameStatus status;

    @NotNull
    @PositiveOrZero
    private Integer hoursPlayed;

    private String notes;

}
