package me.stefanberger.moviememory.dao;

import io.dropwizard.hibernate.AbstractDAO;
import me.stefanberger.moviememory.model.Director;
import org.hibernate.SessionFactory;

public class DirectorDao extends AbstractDAO<Director> {
    public DirectorDao(SessionFactory factory) {
        super(factory);
    }

    public Director create(Director director) {
        return persist(director);
    }

    public Director findById(int id) {
        return get(id);
    }
}
