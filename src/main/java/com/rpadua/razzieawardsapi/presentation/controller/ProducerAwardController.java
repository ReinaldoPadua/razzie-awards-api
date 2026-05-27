package com.rpadua.razzieawardsapi.presentation.controller;

import com.rpadua.razzieawardsapi.application.dto.AwardIntervalResponse;
import com.rpadua.razzieawardsapi.application.usecase.GetProducerAwardIntervalsUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/producers")
public class ProducerAwardController {

    private final GetProducerAwardIntervalsUseCase useCase;

    private static final Logger log =
            LoggerFactory.getLogger(ProducerAwardController.class);

    public ProducerAwardController(
            GetProducerAwardIntervalsUseCase useCase
    ) {
        this.useCase = useCase;
    }

    @GetMapping("/awards/intervals")
    public AwardIntervalResponse getIntervals() {
        log.info("Receiving request to Get Producer Award Intervals");
        return useCase.execute();
    }
}