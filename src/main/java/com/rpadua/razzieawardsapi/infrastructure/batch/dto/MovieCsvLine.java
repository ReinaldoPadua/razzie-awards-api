package com.rpadua.razzieawardsapi.infrastructure.batch.dto;

public record MovieCsvLine(
        Integer year,
        String title,
        String studios,
        String producers,
        String winner
) {
}
