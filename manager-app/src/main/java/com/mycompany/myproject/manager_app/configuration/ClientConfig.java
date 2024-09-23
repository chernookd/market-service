package com.mycompany.myproject.manager_app.configuration;

import com.mycompany.myproject.manager_app.configuration.properties.ClientConfigProperties;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestClient;


@Configuration
@ConfigurationProperties(prefix = "client")
@Data
@RequiredArgsConstructor
public class ClientConfig {


    private final ClientConfigProperties clientConfigProperties;

    @Bean
    public RestClient customRestClient() {

        System.out.println(clientConfigProperties.getBaseUrl() + clientConfigProperties.getPassword() + clientConfigProperties.getUsername());

        return RestClient.builder()
                .baseUrl(clientConfigProperties.getBaseUrl())
                .requestInterceptor(new BasicAuthenticationInterceptor(clientConfigProperties.getUsername(),
                        clientConfigProperties.getPassword()))
                .build();
    }


}
