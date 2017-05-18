package net.marcioguimaraes.dropwizarddemo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DemoConfiguration extends Configuration {

    @JsonProperty
    private String template;

    @JsonProperty
    private String defaultName = "Stranger";

}
