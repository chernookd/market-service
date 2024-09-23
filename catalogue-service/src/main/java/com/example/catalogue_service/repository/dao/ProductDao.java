package com.example.catalogue_service.repository.dao;

import com.example.catalogue_service.dto.Product;

import java.util.List;

public interface ProductDao {

    List<Product> findAll();

    List<Product> findAllByNameLikeIgnoreCase(String filter);

    Product save(Product product);

    void delete(Integer id);

    Product findById(Integer id);

    Product update(Product product);

}
