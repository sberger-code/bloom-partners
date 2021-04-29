package me.stefanberger.moviememory.model;

import me.stefanberger.moviememory.dao.DirectorDao;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Collection;

@Entity(name = DirectorDao.TABLE_NAME)
public class Director extends Filmmaker {

    @OneToMany(mappedBy = "director")
    private Collection<Movie> movies;

    public Collection<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Collection<Movie> movies) {
        this.movies = movies;
    }
}
