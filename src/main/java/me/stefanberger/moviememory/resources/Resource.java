package me.stefanberger.moviememory.resources;

import me.stefanberger.moviememory.model.DomainObject;

import javax.ws.rs.NotFoundException;
import java.util.function.Function;

public abstract class Resource {

    protected <T extends DomainObject> T findOrThrow(int id, Function<Integer, T> load) {
        T foundObject = load.apply(id);
        if (foundObject == null) {
            throw new NotFoundException();
        } else {
            return foundObject;
        }
    }

    protected <T extends DomainObject> T findOrThrow(T domainObject, Function<Integer, T> load) {
        return findOrThrow(domainObject.getId(), load);
    }
}
