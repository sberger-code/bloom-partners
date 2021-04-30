package me.stefanberger.moviememory.resources;

import me.stefanberger.moviememory.dao.AbstractDao;
import me.stefanberger.moviememory.model.AbstractDomainObject;

import javax.validation.Valid;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.util.Collection;
import java.util.function.BiConsumer;

abstract class AbstractResource<T extends AbstractDomainObject> {

    AbstractDao<T> dao;

    AbstractResource(AbstractDao<T> dao) {
        this.dao = dao;
    }

    T create(@Valid T domainObject) {
        if (domainObject == null) {
            throw new BadRequestException("Missing request body");
        } else {
            return dao.create(domainObject);
        }
    }

    Collection<T> findAll() {
        return dao.findAll();
    }

    T findById(int id) {
        return findOrThrow(id, dao);
    }

    T update(int id, T domainObject, BiConsumer<T, T> set) {
        if (domainObject.getId() == 0) {
            domainObject.setId(id);
        } else if (domainObject.getId() != id) {
            throw new BadRequestException("Invalid ID in request body");
        }

        T existingDomainObject = findOrThrow(id, dao);
        set.accept(existingDomainObject, domainObject);

        return dao.create(existingDomainObject);
    }

    void delete(int id) {
        T domainObject = findOrThrow(id, dao);
        dao.delete(domainObject);
    }

    void delete(T domainObject) {
        dao.delete(domainObject);
    }

    <U extends AbstractDomainObject> U findOrThrow(int id, AbstractDao<U> dao) {
        U foundObject = dao.findById(id);
        if (foundObject == null) {
            throw new NotFoundException(dao.getTableName() + " with ID " + id + " not found.");
        } else {
            return foundObject;
        }
    }

    <U extends AbstractDomainObject> U findOrThrow(U domainObject, AbstractDao<U> dao) {
        return findOrThrow(domainObject.getId(), dao);
    }
}
