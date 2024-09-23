package com.example.catalogue_service.service;


import com.example.catalogue_service.dto.Product;

import java.util.List;

public interface ProductService {

    Iterable<Product> findAllProducts();

    List<Product> findAllProductsWithFilter(String filter);

    Product createNewProduct(Integer id, String name, String description);

    Product findProductById(int productId);

    Product updateProduct(Integer id, String name, String description);

    void deleteById(Integer id);
}
