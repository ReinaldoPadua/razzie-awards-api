package com.rpadua.razzieawardsapi.application.dto;

import java.util.List;

public record AwardIntervalResponse(
        List<ProducerIntervalDto> min,
        List<ProducerIntervalDto> max
) {
}
