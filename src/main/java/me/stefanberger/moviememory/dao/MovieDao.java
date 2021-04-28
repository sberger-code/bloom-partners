package me.stefanberger.moviememory.dao;

import io.dropwizard.hibernate.AbstractDAO;
import me.stefanberger.moviememory.model.Movie;
import org.hibernate.SessionFactory;

public class MovieDao extends AbstractDAO<Movie> {
    public MovieDao(SessionFactory factory) {
        super(factory);
    }

    public Movie create(Movie movie) {
        return persist(movie);
    }

    public Movie findById(int id) {
        return get(id);
    }
}
