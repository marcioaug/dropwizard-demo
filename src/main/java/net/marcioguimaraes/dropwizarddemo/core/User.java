package net.marcioguimaraes.dropwizarddemo.core;

import lombok.*;

import java.security.Principal;

@Getter @Setter @NoArgsConstructor @RequiredArgsConstructor
public class User implements Principal {

    @NonNull
    private String username;
    private String password;

    public String getName() {
        return this.username;
    }

}
