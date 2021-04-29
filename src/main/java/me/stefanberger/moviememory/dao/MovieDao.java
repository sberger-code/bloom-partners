package me.stefanberger.moviememory.dao;

import me.stefanberger.moviememory.model.Actor;
import me.stefanberger.moviememory.model.Movie;
import org.hibernate.SessionFactory;

import java.util.Collection;

public class MovieDao extends AbstractDao<Movie> {

    public static final String TABLE_NAME = "Movie";

    public MovieDao(SessionFactory factory) {
        super(factory);
    }

    public Collection<Movie> findByActor(Actor actor) {
        return this.list(query("SELECT a FROM " + getTableName() + " a WHERE COUNT(a.actors) != 0"));
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
}
