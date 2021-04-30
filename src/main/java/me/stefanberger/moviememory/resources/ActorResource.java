package me.stefanberger.moviememory.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import me.stefanberger.moviememory.dao.ActorDao;
import me.stefanberger.moviememory.model.Actor;
import me.stefanberger.moviememory.model.Filmmaker;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Path("/actor")
@Produces(MediaType.APPLICATION_JSON)
public class ActorResource extends FilmmakerResource<Actor> {

    public ActorResource(ActorDao dao) {
        super(dao);
    }

    @Override
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @UnitOfWork
    @Timed
    public Actor create(@Valid Actor actor) {
        return super.create(actor);
    }

    @GET
    @UnitOfWork
    @Timed
    public Collection<Actor> filter(
            @QueryParam("name") String name,
            @QueryParam("movieTitle") String movieTitle
    ) {
        return super.filter(name, movieTitle);
    }

    @Override
    @GET
    @Path("/{id}")
    @UnitOfWork
    @Timed
    public Actor findById(@PathParam("id") int id) {
        return super.findById(id);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @UnitOfWork
    @Timed
    public Actor update(@PathParam("id") int id, @Valid Actor actor) {
        return super.update(id, actor, Filmmaker::set);
    }

    @Override
    @DELETE
    @Path("/{id}")
    @UnitOfWork
    @Timed
    public void delete(@PathParam("id") int id) {
        super.delete(id);
    }
}
