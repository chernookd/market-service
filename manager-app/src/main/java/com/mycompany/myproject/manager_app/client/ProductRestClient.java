package com.mycompany.myproject.manager_app.client;

import com.mycompany.myproject.manager_app.dto.Product;

import java.util.List;
import java.util.Optional;


public interface ProductRestClient {

    List<Product> findAllProduct();

    List<Product> findAllProductWithFilter(String filter);

    Product createProduct(String name, String description);

    Product updateProduct(int productId, String name, String description);

    Optional<Product> findProduct(int productId);

    void deleteProduct(int productId);

}
