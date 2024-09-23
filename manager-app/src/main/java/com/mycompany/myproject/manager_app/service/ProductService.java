package com.mycompany.myproject.manager_app.service;

import com.mycompany.myproject.manager_app.dto.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAllProducts();

    Product createNewProduct(Integer id, String name, String description);

    Product findProductById(int productId);

    Product updateProduct(Integer id, String name, String description);

    void deleteById(Integer id);
}
