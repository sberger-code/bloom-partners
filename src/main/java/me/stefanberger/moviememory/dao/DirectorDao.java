package me.stefanberger.moviememory.dao;

import me.stefanberger.moviememory.model.Director;
import org.hibernate.SessionFactory;

public class DirectorDao extends AbstractDao<Director> {

    public static final String TABLE_NAME = "Director";

    public DirectorDao(SessionFactory factory) {
        super(factory);
    }

    public String getTableName() {
        return TABLE_NAME;
    }
}
