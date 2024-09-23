package com.example.catalogue_service.service.daoService;

import com.example.catalogue_service.dto.Product;
import com.example.catalogue_service.repository.dao.ProductDao;
import com.example.catalogue_service.repository.jdbc.ProductRepositoryJdbc;
import com.example.catalogue_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class JdbcService implements ProductService  {

    ProductDao productRepositoryJdbc;

    @Autowired
    public JdbcService(ProductDao productRepositoryJdbc) {
        this.productRepositoryJdbc = productRepositoryJdbc;
    }


    @Override
    public Iterable<Product> findAllProducts() {
        return productRepositoryJdbc.findAll();
    }

    @Override
    public List<Product> findAllProductsWithFilter(String filter) {

        if (filter!= null && !filter.isBlank()) {
            return productRepositoryJdbc.findAllByNameLikeIgnoreCase(filter);
        }

        return productRepositoryJdbc.findAll();
    }

    @Override
    public Product createNewProduct(Integer id, String name, String description) {
        return productRepositoryJdbc.save(new Product(null, name, description));
    }

    @Override
    public Product findProductById(int productId) {
        return productRepositoryJdbc.findById(productId);
    }

    @Override
    public Product updateProduct(Integer id, String name, String description) {
        return productRepositoryJdbc.update(new Product(id, name, description));
    }

    @Override
    public void deleteById(Integer id) {
        productRepositoryJdbc.delete(id);
    }
}
