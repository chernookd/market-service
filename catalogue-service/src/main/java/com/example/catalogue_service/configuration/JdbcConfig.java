package com.example.catalogue_service.configuration;

import com.example.catalogue_service.configuration.properties.JdbcConfigProperties;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.client.RestClient;

import javax.sql.DataSource;


@Configuration
@ConfigurationProperties(prefix = "client")
@Data
@RequiredArgsConstructor
public class JdbcConfig {


    private final JdbcConfigProperties jdbcConfigProperties;


    @Bean
    public DataSource dataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(jdbcConfigProperties.getUrl());
        dataSource.setUsername(jdbcConfigProperties.getUsername());
        dataSource.setPassword(jdbcConfigProperties.getPassword());
        
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
