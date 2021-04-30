package me.stefanberger.moviememory.model;

import me.stefanberger.moviememory.dao.MovieDao;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;

@Entity(name = MovieDao.TABLE_NAME)
public class Movie extends AbstractDomainObject {

    @NotEmpty
    private String title;

    @NotEmpty
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

    public String getTitle() {
        return title;
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

    public static void set(Movie target, Movie source) {
        target.title = source.title;
        target.releaseYear = source.releaseYear;
        target.duration = source.duration;
        target.actors = source.actors;
        target.director = source.director;
    }
}