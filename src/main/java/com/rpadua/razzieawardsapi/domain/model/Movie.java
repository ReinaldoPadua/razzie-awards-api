package com.rpadua.razzieawardsapi.domain.model;

public final class Movie {

    private final Long id;

    private final Integer year;

    private final String title;

    private final String studios;

    private final String producers;

    private final Boolean winner;

    public Movie(
            Long id,
            Integer year,
            String title,
            String studios,
            String producers,
            Boolean winner
    ) {

        this.id = id;
        this.year = year;
        this.title = title;
        this.studios = studios;
        this.producers = producers;
        this.winner = winner;
    }

    public Long id() {
        return id;
    }

    public Integer year() {
        return year;
    }

    public String title() {
        return title;
    }

    public String studios() {
        return studios;
    }

    public String producers() {
        return producers;
    }

    public Boolean winner() {
        return winner;
    }
}
