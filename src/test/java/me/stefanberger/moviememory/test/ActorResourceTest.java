package me.stefanberger.moviememory.test;

import io.cucumber.junit.platform.engine.Cucumber;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import me.stefanberger.moviememory.dao.ActorDao;
import me.stefanberger.moviememory.model.Actor;
import me.stefanberger.moviememory.resources.ActorResource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@ExtendWith(DropwizardExtensionsSupport.class)
class ActorResourceTest {
    private static final ActorDao DAO = mock(ActorDao.class);
    private static final ResourceExtension EXT = ResourceExtension.builder()
            .addResource(new ActorResource(DAO))
            .build();
    private Actor actor;

    @BeforeEach
    void setup() {
        actor = new Actor();
        actor.setId(1);
    }

    @AfterEach
    void tearDown() {
        reset(DAO);
    }

    @Test
    void getActorSuccess() {
        when(DAO.findById(1)).thenReturn(actor);

        Actor found = EXT.target("/actor/1").request().get(Actor.class);

        assertThat(found.getId()).isEqualTo(actor.getId());
        verify(DAO).findById(1);
    }

    @Test
    void getActorNotFound() {
        when(DAO.findById(2)).thenReturn(null);
        final Response response = EXT.target("/actor/2").request().get();

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
        verify(DAO).findById(2);
    }
}