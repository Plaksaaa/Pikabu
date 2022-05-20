package com.plaxa.pikabu.configuration.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(
        prefix = "firebase"
)
@Getter
@Setter
public class FirebaseProperties {

    private String bucket;
    private String jsonPath;
    private String downloadUrl;
    private String downloadTo;
}
