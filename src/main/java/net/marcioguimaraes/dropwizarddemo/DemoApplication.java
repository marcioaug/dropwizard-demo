package net.marcioguimaraes.dropwizarddemo;

import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.setup.Environment;
import net.marcioguimaraes.dropwizarddemo.auth.DemoAuthenticator;
import net.marcioguimaraes.dropwizarddemo.core.User;
import net.marcioguimaraes.dropwizarddemo.resources.HelloWorldResource;
import net.marcioguimaraes.dropwizarddemo.resources.LoginResource;


public class DemoApplication extends Application<DemoConfiguration> {

    public static void main(String[] args) throws Exception {
        new DemoApplication().run(args);
    }

    public void run(DemoConfiguration configuration, Environment environment) {

        this.configureAuthentication(environment);

        environment.jersey().register(
                new HelloWorldResource(configuration.getTemplate(), configuration.getDefaultName())
        );

        environment.jersey().register(
                new LoginResource()
        );
    }

    private void configureAuthentication(Environment environment) {
        environment.jersey().register(
                new AuthDynamicFeature(
                        new BasicCredentialAuthFilter.Builder<User>()
                                .setAuthenticator(new DemoAuthenticator())
                                .buildAuthFilter()
                )
        );

        environment.jersey().register(
                new AuthValueFactoryProvider.Binder<>(User.class)
        );
    }

}
