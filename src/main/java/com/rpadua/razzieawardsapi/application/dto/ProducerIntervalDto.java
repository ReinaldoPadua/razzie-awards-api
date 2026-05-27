package com.rpadua.razzieawardsapi.application.dto;

public record ProducerIntervalDto(
        String producer,
        Integer interval,
        Integer previousWin,
        Integer followingWin
) {
}
