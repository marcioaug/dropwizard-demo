package net.marcioguimaraes.dropwizarddemo.auth;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;
import net.marcioguimaraes.dropwizarddemo.core.User;

import java.util.Optional;


public class DemoAuthenticator implements Authenticator<BasicCredentials, User> {

    public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {

        if ("secret".equals(credentials.getPassword()))
            return Optional.of(new User(credentials.getUsername()));

        return Optional.empty();
    }

}
