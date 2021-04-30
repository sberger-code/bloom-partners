package me.stefanberger.moviememory.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.hibernate.UnitOfWork;
import me.stefanberger.moviememory.dao.DirectorDao;
import me.stefanberger.moviememory.model.Director;
import me.stefanberger.moviememory.model.Filmmaker;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Path("/director")
@Produces(MediaType.APPLICATION_JSON)
public class DirectorResource extends FilmmakerResource<Director> {

    public DirectorResource(DirectorDao dao) {
        super(dao);
    }

    @Override
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @UnitOfWork
    @Timed
    public Director create(@Valid Director director) {
        return super.create(director);
    }

    @GET
    @Path("/{id}")
    @UnitOfWork
    @Timed
    public Director findById(@PathParam("id") int id) {
        return super.findById(id);
    }

    @GET
    @UnitOfWork
    @Timed
    public Collection<Director> filter(
            @QueryParam("name") String name,
            @QueryParam("movieTitle") String movieTitle
    ) {
        return super.filter(name, movieTitle);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @UnitOfWork
    @Timed
    public Director update(@PathParam("id") int id, @Valid Director director) {
        return super.update(id, director, Filmmaker::set);
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
