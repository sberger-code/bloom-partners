package me.stefanberger.moviememory.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import me.stefanberger.moviememory.dao.DirectorDao;
import me.stefanberger.moviememory.model.Director;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/director")
@Produces(MediaType.APPLICATION_JSON)
public class DirectorResource extends Resource {

    private DirectorDao dao;

    public DirectorResource(DirectorDao dao) {
        this.dao = dao;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @UnitOfWork
    @Timed
    public Director create(Director director) {
        return dao.create(director);
    }

    @GET
    @UnitOfWork
    @Timed
    public Director findById(@QueryParam("id") int id) {
        return findOrThrow(id, dao::findById);
    }
}
