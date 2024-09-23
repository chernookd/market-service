package com.mycompany.myproject.manager_app.repository;
import com.mycompany.myproject.manager_app.dto.Product;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryProductRepositoryImpl implements ProductRepository {

    private final List<Product> products = Collections.synchronizedList(new ArrayList<>());

    @Override
    public List<Product> findAll() {
        return Collections.unmodifiableList(this.products);
    }

    @Override
    public Product save(Product product) {
        int newId = products.stream()
                .max(new Comparator<Product>() {
                    @Override
                    public int compare(Product o1, Product o2) {
                        return o1.getId() - o2.getId();
                    }
                })
                .map(Product::getId)
                .orElse(0) + 1;

        product.setId(newId);
        products.add(product);

        return product;
    }


    @Override
    public Optional<Product> findById(int productId) {

        return products.stream()
                .filter(product -> {
                    return productId == product.getId();
                })
                .findFirst();



    }

    @Override
    public void deleteById(Integer id) {
        products.removeIf(product -> {
            return Objects.equals(product.getId(), id);
        });
    }
}
