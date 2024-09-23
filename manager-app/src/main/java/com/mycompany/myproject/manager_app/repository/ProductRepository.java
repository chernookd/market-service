package com.mycompany.myproject.manager_app.repository;

import com.mycompany.myproject.manager_app.dto.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    public List<Product> findAll();

    Product save(Product product);

    Optional<Product> findById(int productId);

    void deleteById(Integer id);
}
