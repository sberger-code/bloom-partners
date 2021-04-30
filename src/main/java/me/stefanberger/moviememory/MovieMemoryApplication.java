package me.stefanberger.moviememory;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.ScanningHibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import me.stefanberger.moviememory.dao.ActorDao;
import me.stefanberger.moviememory.dao.DirectorDao;
import me.stefanberger.moviememory.dao.MovieDao;
import me.stefanberger.moviememory.resources.ActorResource;
import me.stefanberger.moviememory.resources.DirectorResource;
import me.stefanberger.moviememory.resources.MovieResource;

public class MovieMemoryApplication extends Application<MovieMemoryConfiguration> {
    public static void main(String[] args) throws Exception {
        new MovieMemoryApplication().run(args);
    }

    private final ScanningHibernateBundle<MovieMemoryConfiguration> hibernate =
            new ScanningHibernateBundle<MovieMemoryConfiguration>("me.stefanberger.moviememory.model") {
                @Override
                public DataSourceFactory getDataSourceFactory(MovieMemoryConfiguration configuration) {
                    return configuration.getDataSourceFactory();
                }
            };

    @Override
    public void initialize(Bootstrap<MovieMemoryConfiguration> bootstrap) {
        bootstrap.addBundle(hibernate);
    }

    @Override
    public void run(MovieMemoryConfiguration configuration, Environment environment) {
        ActorDao actorDao = new ActorDao(hibernate.getSessionFactory());
        environment.jersey().register(new ActorResource(actorDao));

        DirectorDao directorDao = new DirectorDao(hibernate.getSessionFactory());
        environment.jersey().register(new DirectorResource(directorDao));

        MovieDao movieDao = new MovieDao(hibernate.getSessionFactory());
        environment.jersey().register(new MovieResource(movieDao, actorDao, directorDao));
    }
}
