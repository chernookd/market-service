package com.mycompany.myproject.manager_app.client;

import com.mycompany.myproject.manager_app.client.dto.NewProductRequest;
import com.mycompany.myproject.manager_app.client.exception.BadRequestException;
import com.mycompany.myproject.manager_app.dto.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;



@Component
public class RestClientImpl implements ProductRestClient {

    private final RestClient restClient;

    private static final Logger logger = LoggerFactory.getLogger(RestClientImpl.class.getName());


    @Autowired
    public RestClientImpl(RestClient restClient) {
        this.restClient = restClient;
    }


    @Override
    public List<Product> findAllProduct() {
        System.out.println(restClient.getClass());


        return restClient
                .get()
                .uri("/catalogue-api/products")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Product>>() {
                });
    }

    @Override
    public List<Product> findAllProductWithFilter(String filter) {
        return restClient
                .get()
                .uri(uriBuilder -> {
                    return uriBuilder.path("/catalogue-api/products/filtered")
                            .queryParam("filter", filter)
                            .build();
                })
                .retrieve()
                .body(new ParameterizedTypeReference<List<Product>>() {
                });
    }


    @Override
    public Product createProduct(String name, String description) {
        try {
            return restClient.post()
                    .uri("/catalogue-api/products")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new NewProductRequest(null, name, description))
                    .retrieve()
                    .body(Product.class);
        } catch (HttpClientErrorException.BadRequest e) {
            ProblemDetail problemDetail = e.getResponseBodyAs(ProblemDetail.class);
            if (problemDetail.getProperties() != null && problemDetail.getProperties().get("errors") != null) {
                throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
            } else {
                throw new RuntimeException();
            }
        }

    }

    @Override
    public Product updateProduct(int productId, String name, String description) {

        logger.info("update product start in service");


        try {
             return restClient.patch()
                    .uri("/catalogue-api/products/update/{productId}", productId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(new NewProductRequest(null, name, description))
                    .retrieve()
                    .body(Product.class);
        } catch (HttpClientErrorException.BadRequest e) {

            logger.info(e.getMessage());

            ProblemDetail problemDetail = e.getResponseBodyAs(ProblemDetail.class);
            if (problemDetail.getProperties() != null && problemDetail.getProperties().get("errors") != null) {
                throw new BadRequestException((List<String>) problemDetail.getProperties().get("errors"));
            }
        }
        logger.info("Всё плохо");

        throw new RuntimeException();
    }

    @Override
    public Optional<Product> findProduct(int productId) {
        try {
            return Optional.ofNullable(restClient.get()
                    .uri("/catalogue-api/products/{productId}", productId)
                    .retrieve()
                    .body(Product.class));
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteProduct(int productId) {
        try {
            restClient.delete()
                    .uri("/catalogue-api/products/delete/{productId}", productId)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpClientErrorException.NotFound e) {
            throw new NoSuchElementException(e);
        }
    }
}
