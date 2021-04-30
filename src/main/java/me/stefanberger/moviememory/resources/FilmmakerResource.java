package me.stefanberger.moviememory.resources;

import me.stefanberger.moviememory.dao.AbstractDao;
import me.stefanberger.moviememory.model.Actor;
import me.stefanberger.moviememory.model.Filmmaker;

import javax.ws.rs.BadRequestException;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class FilmmakerResource<T extends Filmmaker> extends AbstractResource<T> {

    public FilmmakerResource(AbstractDao<T> dao) {
        super(dao);
    }

    @Override
    void delete(int id) {
        T domainObject = findOrThrow(id, dao);
        if (domainObject.getMovies().size() == 0) {
            super.delete(domainObject);
        } else {
            throw new BadRequestException("Resource is referenced");
        }
    }

    Collection<T> filter(String name, String movieTitle) {
        Stream<T> filmmakerStream = findAll().stream();
        filmmakerStream = filterBy(filmmakerStream, name, (filmmaker) -> filmmaker.getName().contains(name));
        filmmakerStream = filterBy(filmmakerStream, movieTitle, (filmmaker) -> filmmaker.getMovies().stream().anyMatch((movie) -> movie.getTitle().contains(movieTitle)));
        return filmmakerStream.collect(Collectors.toList());
    }

    private <U> Stream<T> filterBy(Stream<T> actorStream, U condition, Function<T, Boolean> compare) {
        if (condition == null) {
            return actorStream;
        } else {
            return actorStream.filter(compare::apply);
        }
    }
}
