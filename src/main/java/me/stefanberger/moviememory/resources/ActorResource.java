package me.stefanberger.moviememory.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import me.stefanberger.moviememory.dao.ActorDao;
import me.stefanberger.moviememory.model.Actor;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/actor")
@Produces(MediaType.APPLICATION_JSON)
public class ActorResource extends Resource {

    private ActorDao dao;

    public ActorResource(ActorDao dao) {
        this.dao = dao;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @UnitOfWork
    @Timed
    public Actor create(Actor actor) {
        return dao.create(actor);
    }

    @GET
    @UnitOfWork
    @Timed
    public Actor findById(@QueryParam("id") int id) {
        return findOrThrow(id, dao::findById);
    }
}
