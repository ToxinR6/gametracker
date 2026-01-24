package com.example.gametracker.dto;

import com.example.gametracker.entity.GameStatus;
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

    @NotNull
    private GameStatus status;

    @NotNull
    @PositiveOrZero
    private Integer hoursPlayed;

    private String notes;

}
