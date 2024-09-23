package com.mycompany.myproject.manager_app.service;


import com.mycompany.myproject.manager_app.dto.Product;
import com.mycompany.myproject.manager_app.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class DefaultServiceImpl implements ProductService {

    private final ProductRepository inMemoryRepository;

    @Autowired
    public DefaultServiceImpl(ProductRepository inMemoryRepository) {
        this.inMemoryRepository = inMemoryRepository;
    }


    @Override
    public List<Product> findAllProducts() {
        return Collections.unmodifiableList(inMemoryRepository.findAll());
    }

    @Override
    public Product createNewProduct(Integer id, String name, String description) {

        return inMemoryRepository.save(new Product(null, name, description));
    }

    @Override
    public Product findProductById(int productId) {
        Optional<Product> productOptional = inMemoryRepository.findById(productId);

        return productOptional.orElseThrow();
    }

    @Override
    public Product updateProduct(Integer id, String name, String description) throws NoSuchElementException {
        Optional<Product> productOptional = inMemoryRepository.findById(id);

        productOptional.ifPresentOrElse(product -> {
            product.setId(id);
            product.setName(name);
            product.setDescription(description);
        }, () -> {
            throw new NoSuchElementException();
        });

        return productOptional.get();
    }

    @Override
    public void deleteById(Integer id) {
        inMemoryRepository.deleteById(id);
    }
}
