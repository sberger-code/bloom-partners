package me.stefanberger.moviememory.resources;

import com.codahale.metrics.annotation.Timed;
import me.stefanberger.moviememory.model.Movie;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

@Path("/movie")
@Produces(MediaType.APPLICATION_JSON)
public class MovieResource {
    private final String template;
    private final String defaultName;

    public MovieResource(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;
    }

    @GET
    @Timed
    public Movie getMovie(@QueryParam("id") Optional<Long> id) {
        return new Movie();
    }
}