package me.stefanberger.moviememory.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Movie extends DomainObject {

    private String name;

    private int releaseYear;

    private int duration;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "movie_actor",
            joinColumns = {@JoinColumn(name = "movie_id")},
            inverseJoinColumns = {@JoinColumn(name = "actor_id")}
    )
    private Collection<Actor> actors;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "director_id")
    private Director director;

    public Movie() {
        // Jackson deserialization
    }

    public Movie(int id, String name, int releaseYear, int duration, Collection<Actor> actors, Director director) {
        this.id = id;
        this.name = name;
        this.releaseYear = releaseYear;
        this.duration = duration;
        this.actors = actors;
        this.director = director;
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

    public void setActors(Collection<Actor> actors) {
        this.actors = actors;
    }

    public Collection<Actor> getActors() {
        return actors;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public Director getDirector() {
        return director;
    }
}