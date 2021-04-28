package me.stefanberger.moviememory.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import me.stefanberger.moviememory.dao.ActorDao;
import me.stefanberger.moviememory.dao.DirectorDao;
import me.stefanberger.moviememory.dao.MovieDao;
import me.stefanberger.moviememory.model.Movie;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.stream.Collectors;

@Path("/movie")
@Produces(MediaType.APPLICATION_JSON)
public class MovieResource extends Resource {

    private MovieDao dao;
    private ActorDao actorDao;
    private DirectorDao directorDao;

    public MovieResource(MovieDao dao, ActorDao actorDao, DirectorDao directorDao) {
        this.dao = dao;
        this.actorDao = actorDao;
        this.directorDao = directorDao;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @UnitOfWork
    @Timed
    public Movie create(Movie movie) {
        if (movie.getActors() != null) {
            movie.setActors(movie.getActors().stream().map(actor ->
                    actor.getId() == 0 ? actor : findOrThrow(actor, actorDao::findById))
                    .collect(Collectors.toSet()));
        }

        if (movie.getDirector() != null && movie.getDirector().getId() != 0) {
            movie.setDirector(findOrThrow(movie.getDirector(), directorDao::findById));
        }

        return dao.create(movie);
    }

    @GET
    @UnitOfWork
    @Timed
    public Movie findById(@QueryParam("id") int id) {
        return findOrThrow(id, dao::findById);
    }
}