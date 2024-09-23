package com.example.catalogue_service.repository.jdbc;

import com.example.catalogue_service.dto.Product;
import com.example.catalogue_service.repository.dao.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public class ProductRepositoryJdbc implements ProductDao {

    private static final String FIND_ALL_SQL = "SELECT * FROM products";

    private static final String FIND_ALL_BY_NAME_LIKE_IGNORE_CASE_SQL = "SELECT * FROM products WHERE LOWER(name) LIKE LOWER(?)";

    private static final String SAVE_SQL = "INSERT INTO products (name, description) VALUES (?, ?) RETURNING id";

    private static final String DELETE_SQL = "DELETE FROM products WHERE id = ?";

    private static final String FIND_BY_ID_SQL = "SELECT * FROM products WHERE id = ?";

    private static final String UPDATE_SQL = "UPDATE products SET name = ?, description = ? WHERE id = ?";;


    private final JdbcTemplate jdbcTemplate;



    @Autowired
    public ProductRepositoryJdbc (JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    private RowMapper<Product> productRowMapper = (rs, rowNum) ->
            new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description")
            );

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, productRowMapper);
    }

    @Override
    public List<Product> findAllByNameLikeIgnoreCase(String filter) {
        return jdbcTemplate.query(FIND_ALL_BY_NAME_LIKE_IGNORE_CASE_SQL, productRowMapper, filter);
    }

    @Override
    public Product save(Product product) {
         jdbcTemplate.update(SAVE_SQL, Integer.class, product.getName(), product.getDescription());

         return product;
    }

    @Override
    public void delete(Integer id) {
         jdbcTemplate.update(DELETE_SQL);
    }

    @Override
    public Product findById(Integer id) {
        return jdbcTemplate.queryForObject(FIND_BY_ID_SQL, productRowMapper, id);
    }

    @Override
    public Product update(Product product) {
        jdbcTemplate.update(UPDATE_SQL, product.getName(), product.getDescription(), product.getId());

        return product;
    }

}
