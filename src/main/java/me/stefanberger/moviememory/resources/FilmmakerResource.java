package me.stefanberger.moviememory.resources;

import me.stefanberger.moviememory.dao.AbstractDao;
import me.stefanberger.moviememory.model.AbstractDomainObject;
import me.stefanberger.moviememory.model.Filmmaker;
import me.stefanberger.moviememory.model.Movie;

import javax.ws.rs.BadRequestException;
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

abstract class FilmmakerResource<T extends Filmmaker> extends AbstractResource<T> {

    FilmmakerResource(AbstractDao<T> dao) {
        super(dao);
    }

    @Override
    void delete(int id) {
        T filmmaker = findOrThrow(id, dao);
        Collection<Movie> movies = filmmaker.getMovies();
        if (movies.size() == 0) {
            delete(filmmaker);
        } else {
            Integer[] movieIds = movies.stream().map(AbstractDomainObject::getId).toArray(Integer[]::new);
            throw new BadRequestException("Entity is referenced by movie resource(s) " + Arrays.toString(movieIds));
        }
    }

    Collection<T> filter(String name, String movieTitle) {
        Stream<T> filmmakerStream = findAll().stream();
        filmmakerStream = filterBy(filmmakerStream, name, (filmmaker) -> filmmaker.getName().toLowerCase().contains(name.toLowerCase()));
        filmmakerStream = filterBy(filmmakerStream, movieTitle, (filmmaker) ->
                filmmaker.getMovies().stream().anyMatch((movie) -> movie.getTitle().toLowerCase().contains(movieTitle.toLowerCase())));
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
