package net.marcioguimaraes.dropwizarddemo.resources;

import lombok.NoArgsConstructor;
import org.apache.http.client.utils.URIBuilder;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

@Path("login")
@Produces(MediaType.APPLICATION_JSON)
@NoArgsConstructor
public class LoginResource {

    @GET
    public Response login(@QueryParam("code") String code) throws OAuthSystemException, OAuthProblemException, URISyntaxException, MalformedURLException {
        OAuthClientRequest request = OAuthClientRequest
                .tokenProvider(OAuthProviderType.SALESFORCE)
                .setGrantType(GrantType.AUTHORIZATION_CODE)
                .setClientId("")
                .setClientSecret("")
                .setRedirectURI("http://localhost:8080/login")
                .setCode(code)
                .buildBodyMessage();

        OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
        OAuthAccessTokenResponse response = oAuthClient.accessToken(request);

        String accessToken = response.getAccessToken();

//        System.out.println("----------------------------");
//        System.out.println(accessToken);
//        System.out.println(response.getBody());
//
//        request = new OAuthBearerClientRequest("https://login.salesforce.com/services/oauth2/userinfo")
//                .setAccessToken(accessToken)
//                .buildQueryMessage();
//
//        System.out.println(request.getLocationUri());
//        System.out.println(request.getBody());
//        System.out.println(request.getHeaders());
//
//        OAuthResourceResponse r = oAuthClient.resource(request, OAuth.HttpMethod.GET, OAuthResourceResponse.class);

        URIBuilder uri = new URIBuilder("https://na35.salesforce.com/services/data/v35.0/query");
        uri.addParameter("q", "SELECT Id, Name, Type, Industry, Rating FROM Account");

        String url = uri.build().toURL().toString();

        System.out.println(accessToken);

        OAuthClientRequest req = new OAuthBearerClientRequest(url)
                .buildQueryMessage();

        req.addHeader("Authorization", "Bearer " + accessToken);


        OAuthResourceResponse r = oAuthClient.resource(req, OAuth.HttpMethod.GET, OAuthResourceResponse.class);

        System.out.println(r.getBody());
        return Response.ok(r.getBody()).build();

    }
}
