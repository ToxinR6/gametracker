package com.example.gametracker.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameResponseDTO {
    private Long id;
    private String title;
    private String status;
    private int hoursPlayed;
    private String notes;
    private String imageUrl;
}

