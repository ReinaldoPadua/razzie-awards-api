package com.rpadua.razzieawardsapi.integration;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
class ProducerAwardIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnProducerIntervals()
            throws Exception {

        mockMvc.perform(
                        get("/api/v1/producers/awards/intervals"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.min").exists())
                .andExpect(jsonPath("$.max").exists());
    }
}
