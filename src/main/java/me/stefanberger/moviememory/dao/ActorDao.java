package me.stefanberger.moviememory.dao;

import me.stefanberger.moviememory.model.Actor;
import org.hibernate.SessionFactory;

public class ActorDao extends AbstractDao<Actor> {

    public static final String TABLE_NAME = "Actor";

    public ActorDao(SessionFactory factory) {
        super(factory);
    }

    public String getTableName() {
        return TABLE_NAME;
    }
}
