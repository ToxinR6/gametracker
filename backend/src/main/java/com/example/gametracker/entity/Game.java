package com.example.gametracker.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "games")
@Getter
@Setter
@NoArgsConstructor
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(unique = true)
    private Long steamAppId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GameSource source;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GameStatus status;

    private String imageUrl;

    private int hoursPlayed;

    @Column(length = 500)
    private String notes;


}
