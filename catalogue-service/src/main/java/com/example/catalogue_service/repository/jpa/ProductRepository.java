package com.example.catalogue_service.repository.jpa;


import com.example.catalogue_service.dto.Product;
import com.example.catalogue_service.repository.jpa.ProductEntity.ProductEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<ProductEntity, Integer> {

    List<Product> findAllByNameLikeIgnoreCase(String name);

}
