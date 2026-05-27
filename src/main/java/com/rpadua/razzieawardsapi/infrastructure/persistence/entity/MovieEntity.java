package com.rpadua.razzieawardsapi.infrastructure.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "movies")
public class MovieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "release_year")   // ← mapeia para nome diferente no banco
    private Integer releaseYear;   ;

    private String title;

    private String studios;

    @Column(length = 2000)
    private String producers;

    private Boolean winner;

    protected MovieEntity() {
    }

    public MovieEntity(
            Integer releaseYear,
            String title,
            String studios,
            String producers,
            Boolean winner
    ) {

        this.releaseYear = releaseYear;
        this.title = title;
        this.studios = studios;
        this.producers = producers;
        this.winner = winner;
    }

    public Long getId() {
        return id;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public String getTitle() {
        return title;
    }

    public String getStudios() {
        return studios;
    }

    public String getProducers() {
        return producers;
    }

    public Boolean getWinner() {
        return winner;
    }
}
