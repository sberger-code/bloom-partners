package me.stefanberger.moviememory.model;

import javax.persistence.MappedSuperclass;
import java.util.Collection;
import java.util.stream.Collectors;

@MappedSuperclass
public abstract class Filmmaker extends DomainObject {

    protected String name;

    public String getName() {
        return name;
    }

    protected Collection<String> moviesToFilmography(Collection<Movie> movies) {
        if (movies == null) {
            return null;
        } else {
            return movies.stream().map(movie -> movie.getName() + (" (" + movie.getReleaseYear() + ")")).collect(Collectors.toSet());
        }
    }
}
