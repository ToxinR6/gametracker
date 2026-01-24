package com.example.gametracker.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameResponseDTO {

    private Long id;
    private String status;
    private String title;
    private Integer hoursPlayed;
    private String notes;

}
