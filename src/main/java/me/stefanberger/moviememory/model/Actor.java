package me.stefanberger.moviememory.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Collection;

@Entity
@JsonAutoDetect
public class Actor extends Filmmaker {

    public Actor() {
        // Jackson deserialization
    }

    @ManyToMany(mappedBy = "actors")
    private Collection<Movie> movies;

    @JsonProperty("filmography")
    public Collection<String> getFilmography() {
        return moviesToFilmography(movies);
    }
}
