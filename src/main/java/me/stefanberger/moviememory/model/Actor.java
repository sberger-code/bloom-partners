package me.stefanberger.moviememory.model;

import me.stefanberger.moviememory.dao.ActorDao;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Collection;

@Entity(name = ActorDao.TABLE_NAME)
public class Actor extends Filmmaker {

    @ManyToMany(mappedBy = "actors")
    private Collection<Movie> movies;

    @Override
    public Collection<Movie> getMovies() {
        return movies;
    }

    @Override
    public void setMovies(Collection<Movie> movies) {
        this.movies = movies;
    }
}
