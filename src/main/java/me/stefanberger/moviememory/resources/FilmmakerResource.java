package me.stefanberger.moviememory.resources;

import me.stefanberger.moviememory.dao.AbstractDao;
import me.stefanberger.moviememory.model.Filmmaker;

import javax.ws.rs.BadRequestException;

public abstract class FilmmakerResource<T extends Filmmaker> extends AbstractResource<T> {

    public FilmmakerResource(AbstractDao<T> dao) {
        super(dao);
    }

    @Override
    public void delete(int id) {
        T domainObject = findOrThrow(id, dao);
        if (domainObject.getMovies().size() == 0) {
            super.delete(domainObject);
        } else {
            throw new BadRequestException("Resource is referenced");
        }
    }
}
