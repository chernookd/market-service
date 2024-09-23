package com.example.catalogue_service.controller;


import com.example.catalogue_service.controller.dto.RequestProduct;
import com.example.catalogue_service.controller.exception.CustomExceptionHandler;
import com.example.catalogue_service.dto.Product;
import com.example.catalogue_service.service.ProductService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/catalogue-api/products")
@CustomExceptionHandler
@Slf4j
public class ProductsController {

    private final ProductService defaultServiceImpl;

    @Autowired
    public ProductsController(ProductService jpaService) {
        this.defaultServiceImpl = jpaService;
    }


    @GetMapping
    public List<Product> getAllProduct() {
        log.info("get all product");

        Iterable<Product> iterable = defaultServiceImpl.findAllProducts();
        List<Product> productList = new ArrayList<>();
        iterable.forEach(productList::add);

        return productList;
    }

    @GetMapping(value = "/filtered", params = "filter")
    List<Product> getAllProductWithFilter(@RequestParam(name = "filter", required = true) String filter) {
        log.info("get all product with filter = " + filter);

        return defaultServiceImpl.findAllProductsWithFilter(filter);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> createProduct(@Valid @RequestBody RequestProduct requestProduct, BindingResult bindingResult,
                                                 UriComponentsBuilder uriComponentsBuilder) {

        log.info("create product with id " + requestProduct.getId());


        if (bindingResult.hasErrors()) {
            log.warn("validation error when creating product with id " + requestProduct.getId());


            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new Product(requestProduct.getId(), requestProduct.getName(), requestProduct.getDescription()));
        } else {
            Product product = defaultServiceImpl.createNewProduct(requestProduct.getId(), requestProduct.getName(), requestProduct.getDescription());

            return ResponseEntity
                    .created(uriComponentsBuilder
                            .replacePath("/catalogue-api/products/{productId}")
                            .build(Map.of("productId", product.getId())))
                    .body(product);
        }

    }




}
