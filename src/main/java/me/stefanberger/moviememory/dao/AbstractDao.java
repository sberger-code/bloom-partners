package me.stefanberger.moviememory.dao;

import io.dropwizard.hibernate.AbstractDAO;
import me.stefanberger.moviememory.model.AbstractDomainObject;
import org.hibernate.SessionFactory;

import java.util.Collection;

public abstract class AbstractDao<T extends AbstractDomainObject> extends AbstractDAO<T> {

    public AbstractDao(SessionFactory factory) {
        super(factory);
    }

    public T create(T domainObject) {
        return persist(domainObject);
    }

    public T findById(int id) {
        return get(id);
    }

    public Collection<T> findAll() {
        return list(query("SELECT x FROM " + getTableName() + " x"));
    }

    public void delete(T domainObject) {
        currentSession().delete(domainObject);
    }

    public abstract String getTableName();
}
