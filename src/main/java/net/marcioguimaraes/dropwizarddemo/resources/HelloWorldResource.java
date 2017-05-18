package net.marcioguimaraes.dropwizarddemo.resources;


import com.codahale.metrics.annotation.Timed;
import io.dropwizard.auth.Auth;
import lombok.RequiredArgsConstructor;
import net.marcioguimaraes.dropwizarddemo.api.Saying;
import net.marcioguimaraes.dropwizarddemo.core.User;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.ResponseType;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Path("/hello-word")
@Produces(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class HelloWorldResource {

    private final String template;
    private final String defaultName;
    private final AtomicLong counter = new AtomicLong();

    @GET
    @Timed
    public Saying sayHello(@Auth User principal, @QueryParam("name") Optional<String> name) {
        final String value = String.format(this.template, name.orElse(defaultName));

        return new Saying(this.counter.incrementAndGet(), principal.getUsername());
    }

    @GET @Path("salesforce")
    public Response salesforce(@Auth User principal) throws OAuthSystemException {

        OAuthClientRequest request = OAuthClientRequest
                .authorizationProvider(OAuthProviderType.SALESFORCE)
                .setClientId("3MVG9szVa2RxsqBZmNjiSyEh6FTJurWdIoRlBdv.IUMCIn3bO4aeG2IDVln6UyYliNirUrrAr3ZxHd7iXS7jq")
                .setRedirectURI("http://localhost:8080/login")
                .setResponseType(ResponseType.CODE.toString())
                .buildQueryMessage();

        return Response.temporaryRedirect(URI.create(request.getLocationUri())).build();
    }
}
