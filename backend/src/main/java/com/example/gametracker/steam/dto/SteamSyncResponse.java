package com.example.gametracker.steam.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SteamSyncResponse {
    private String steamInput;
    private String steamId;
    private int totalGames;
    private int createdCount;
    private int updatedCount;
    private List<Long> sampleAppIds;
    private List<String> sampleTitles;
}
