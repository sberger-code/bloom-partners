package me.stefanberger.moviememory.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collection;

public class Movie {
    private long id;
    private String name;
    private int releaseYear;
    private int duration;
    private Collection<Filmmaker> actor;
    private Filmmaker director;
    private Soundtrack soundtrack;

    public Movie() {
        // Jackson deserialization
    }

    public Movie(long id, String name, int releaseYear, int duration, Collection<Filmmaker> actor, Filmmaker director, Soundtrack soundtrack) {
        this.id = id;
        this.name = name;
        this.releaseYear = releaseYear;
        this.duration = duration;
        this.actor = actor;
        this.director = director;
        this.soundtrack = soundtrack;
    }

    @JsonProperty
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public int getDuration() {
        return duration;
    }

    public Collection<Filmmaker> getActor() {
        return actor;
    }

    public Filmmaker getDirector() {
        return director;
    }

    public Soundtrack getSoundtrack() {
        return soundtrack;
    }
}