package com.example.catalogue_service.configuration.properties;


import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "data-source")
//TODO  @ConditionalOnProperty
@Data
public class JdbcConfigProperties {
    private String url;
    private String username;
    private String password;

}

