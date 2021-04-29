package me.stefanberger.moviememory.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.stream.Collectors;

@MappedSuperclass
public abstract class Filmmaker extends AbstractDomainObject {

    @NotEmpty
    protected String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // return filmography instead of a Collection of Movies to avoid a cyclic JSON response
    @JsonProperty("filmography")
    public Collection<String> getFilmography() {
        if (getMovies() == null) {
            return null;
        } else {
            return getMovies().stream().map(movie -> movie.getTitle() + (" (" + movie.getReleaseYear() + ")")).collect(Collectors.toSet());
        }
    }

    public abstract void setMovies(Collection<Movie> movies);

    @JsonIgnore
    public abstract Collection<Movie> getMovies();

    public static void set(Filmmaker target, Filmmaker source) {
        target.id = source.id;
        target.name = source.name;
        target.setMovies(source.getMovies());
    }
}
