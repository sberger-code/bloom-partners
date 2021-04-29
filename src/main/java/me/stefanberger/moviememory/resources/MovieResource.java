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
import java.util.Optional;
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
            @QueryParam("title") Optional<String> title,
            @QueryParam("releaseYear") Optional<Integer> releaseYear,
            @QueryParam("duration") Optional<Integer> duration,
            @QueryParam("actor") Optional<String> actor,
            @QueryParam("director") Optional<String> director) {

        Collection<Movie> movies = super.findAll();
        Stream<Movie> movieStream = movies.stream();

        if (title.isPresent()) {
            movieStream = movieStream.filter((movie) -> movie.getTitle().contains(title.get()));
        }

        if (releaseYear.isPresent()) {
            movieStream = movieStream.filter((movie) -> movie.getReleaseYear() == releaseYear.get());
        }

        if (duration.isPresent()) {
            movieStream = movieStream.filter((movie) -> movie.getDuration() == duration.get());
        }

        if (actor.isPresent()) {
            movieStream = movieStream.filter((movie) -> movie.getActors().stream().anyMatch((a) -> a.getName().contains(actor.get())));
        }

        if (director.isPresent()) {
            movieStream = movieStream.filter((movie) -> movie.getDirector().getName().contains(director.get()));
        }

        return movieStream.collect(Collectors.toList());
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
