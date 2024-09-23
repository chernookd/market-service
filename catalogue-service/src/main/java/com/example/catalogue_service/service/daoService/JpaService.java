package com.example.catalogue_service.service.daoService;



import com.example.catalogue_service.dto.Product;
import com.example.catalogue_service.repository.jpa.ProductEntity.ProductEntity;
import com.example.catalogue_service.repository.jpa.ProductRepository;
import com.example.catalogue_service.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class JpaService implements ProductService {

    private final ProductRepository productRepository;



    @Autowired
    public JpaService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public List<Product> findAllProducts() throws NoSuchElementException  {
        List<Product> productList = new ArrayList<>();

        productRepository.findAll().forEach(productEntity -> {
            productList.add(new Product(productEntity.getId(), productEntity.getName(), productEntity.getDescription()));
        });


        return productList;
    }

    @Override
    public List<Product> findAllProductsWithFilter(String filter) {

        if (filter != null && !filter.isBlank()) {
            return productRepository.findAllByNameLikeIgnoreCase(filter);
        }

        List<Product> productList = new ArrayList<>();
        productRepository.findAll().forEach(productEntity -> {
            productList.add(new Product(productEntity.getId(), productEntity.getName(), productEntity.getDescription()));
        });

        return productList;
    }

    @Override
    @Transactional
    public Product createNewProduct(Integer id, String name, String description) {
        ProductEntity savedEntity = productRepository.save(new ProductEntity(null, name, description));

        return new Product(savedEntity.getId(), savedEntity.getName(), savedEntity.getDescription());
    }

    @Override
    public Product findProductById(int productId) throws NoSuchElementException {
        ProductEntity productEntity = productRepository.findById(productId).orElseThrow();

        return new Product(productEntity.getId(), productEntity.getName(), productEntity.getDescription());
    }

    @Override
    @Transactional
    public Product updateProduct(Integer id, String name, String description) throws NoSuchElementException {
        Optional<ProductEntity> productOptional = productRepository.findById(id);

        System.out.println(productOptional.get().getDescription() + productOptional.get().getId());

        productOptional.ifPresentOrElse(product -> {
            product.setId(id);
            product.setName(name);
            product.setDescription(description);
        }, () -> {
            throw new NoSuchElementException();
        });



        return new Product(productOptional.get().getId(), productOptional.get().getName(), productOptional.get().getDescription());
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        productRepository.deleteById(id);
    }
}
