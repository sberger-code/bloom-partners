package me.stefanberger.moviememory;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import me.stefanberger.moviememory.resources.HelloWorldResource;

public class MovieMemoryApplication extends Application<MovieMemoryConfiguration> {
    public static void main(String[] args) throws Exception {
        new MovieMemoryApplication().run(args);
    }

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<MovieMemoryConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(MovieMemoryConfiguration configuration,
                    Environment environment) {
        final HelloWorldResource resource = new HelloWorldResource(
                configuration.getTemplate(),
                configuration.getDefaultName()
        );
        final TemplateHealthCheck healthCheck =
                new TemplateHealthCheck(configuration.getTemplate());
        environment.healthChecks().register("template", healthCheck);
        environment.jersey().register(resource);
    }
}
