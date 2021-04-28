package me.stefanberger.moviememory.dao;

import io.dropwizard.hibernate.AbstractDAO;
import me.stefanberger.moviememory.model.Actor;
import org.hibernate.SessionFactory;

public class ActorDao extends AbstractDAO<Actor> {
    public ActorDao(SessionFactory factory) {
        super(factory);
    }

    public Actor create(Actor actor) {
        return persist(actor);
    }

    public Actor findById(int id) {
        return get(id);
    }
}
