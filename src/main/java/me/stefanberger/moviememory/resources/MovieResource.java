package me.stefanberger.moviememory.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import me.stefanberger.moviememory.dao.ActorDao;
import me.stefanberger.moviememory.dao.DirectorDao;
import me.stefanberger.moviememory.dao.MovieDao;
import me.stefanberger.moviememory.model.Movie;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Path("/movie")
@Produces(MediaType.APPLICATION_JSON)
public class MovieResource extends AbstractResource<Movie> {

    private ActorDao actorDao;
    private DirectorDao directorDao;

    public MovieResource(MovieDao dao, ActorDao actorDao, DirectorDao directorDao) {
        super(dao);
        this.actorDao = actorDao;
        this.directorDao = directorDao;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @UnitOfWork
    @Timed
    public Movie create(@Valid Movie movie) {
        loadFilmmakers(movie);
        return dao.create(movie);
    }

    @Override
    @GET
    @Path("/{id}")
    @UnitOfWork
    @Timed
    public Movie findById(@PathParam("id") int id) {
        return super.findById(id);
    }

    @GET
    @UnitOfWork
    @Timed
    public Collection<Movie> filter(
            @QueryParam("title") String title,
            @QueryParam("releaseYear") Integer releaseYear,
            @QueryParam("duration") Integer duration,
            @QueryParam("actorName") String actorName,
            @QueryParam("directorName") String directorName
    ) {
        Stream<Movie> movieStream = findAll().stream();
        movieStream = filterBy(movieStream, title, (movie) -> movie.getTitle().toLowerCase().contains(title.toLowerCase()));
        movieStream = filterBy(movieStream, releaseYear, (movie) -> movie.getReleaseYear() == releaseYear);
        movieStream = filterBy(movieStream, duration, (movie) -> movie.getDuration() == duration);
        movieStream = filterBy(movieStream, actorName, (movie) -> movie.getActors().stream().anyMatch((actor) -> actor.getName().toLowerCase().contains(actorName.toLowerCase())));
        movieStream = filterBy(movieStream, directorName, (movie) -> movie.getDirector().getName().toLowerCase().contains(directorName.toLowerCase()));
        return movieStream.collect(Collectors.toList());
    }

    private <T> Stream<Movie> filterBy(Stream<Movie> movieStream, T condition, Function<Movie, Boolean> compare) {
        if (condition == null) {
            return movieStream;
        } else {
            return movieStream.filter(compare::apply);
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @UnitOfWork
    @Timed
    public Movie update(@PathParam("id") int id, @Valid Movie movie) {
        return super.update(id, movie, (Movie target, Movie source) -> {
            Movie.set(target, source);
            loadFilmmakers(target);
        });
    }

    @DELETE
    @Path("/{id}")
    @UnitOfWork
    @Timed
    public void delete(@PathParam("id") int id) {
        super.delete(id);
    }

    private void loadFilmmakers(Movie movie) {
        if (movie.getActors() != null) {
            movie.setActors(movie.getActors().stream().map(actor ->
                    actor.getId() == 0 ? actor : findOrThrow(actor, actorDao))
                    .collect(Collectors.toSet()));
        }

        if (movie.getDirector() != null && movie.getDirector().getId() != 0) {
            movie.setDirector(findOrThrow(movie.getDirector(), directorDao));
        }
    }
}
