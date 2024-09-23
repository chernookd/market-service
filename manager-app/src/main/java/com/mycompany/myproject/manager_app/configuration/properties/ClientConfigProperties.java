package com.mycompany.myproject.manager_app.configuration.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "client")
@PropertySource("classpath:application.yaml")
@Data
public class ClientConfigProperties {
    private String baseUrl;
    private String username;
    private String password;
}


