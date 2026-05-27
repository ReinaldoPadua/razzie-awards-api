package com.rpadua.razzieawardsapi.domain.model;

public record ProducerAwardInterval(
        String producer,
        Integer interval,
        Integer previousWin,
        Integer followingWin
) {
}
