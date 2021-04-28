package me.stefanberger.moviememory.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Collection;

@Entity
public class Director extends Filmmaker {

    public Director() {
        // Jackson deserialization
    }

    @OneToMany(mappedBy = "director")
    private Collection<Movie> movies;

    @JsonProperty("filmography")
    public Collection<String> getFilmography() {
        return moviesToFilmography(movies);
    }
}
