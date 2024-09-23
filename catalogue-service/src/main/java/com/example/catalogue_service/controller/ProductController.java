package com.example.catalogue_service.controller;


import com.example.catalogue_service.controller.dto.RequestProduct;
import com.example.catalogue_service.controller.exception.CustomExceptionHandler;
import com.example.catalogue_service.dto.Product;
import com.example.catalogue_service.service.ProductService;
import jakarta.validation.Valid;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@CustomExceptionHandler
@RequestMapping("catalogue-api/products")
@Slf4j
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class.getName());

    private final ProductService defaultServiceImpl;


    @ModelAttribute (value = "product", binding = false)
    public Product product(@PathVariable("productId") int productId) {
        return this.defaultServiceImpl.findProductById(productId);
    }

    @Autowired
    public ProductController(ProductService defaultServiceImpl) {
        this.defaultServiceImpl = defaultServiceImpl;
    }


    @GetMapping("/{productId}")
    public Product findProduct(@ModelAttribute("product") Product product) {
        log.info("find product with id" + product.getId());

        return product;
    }

    @PatchMapping("/update/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable("productId") int productId,
                                              @Valid @RequestBody RequestProduct requestProduct,
                                              BindingResult result) {
        log.info("update product with id " + productId);

        requestProduct.setId(productId);
        if (result.hasErrors()) {
            log.warn("validation error when update product with id " + productId);

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            Product product = defaultServiceImpl
                    .updateProduct(requestProduct.getId(), requestProduct.getName(), requestProduct.getDescription());


            return ResponseEntity.ok().body(product);
        }
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable("productId") int productId) {
        log.info("delete product with id " + productId);

        defaultServiceImpl.deleteById(productId);
        return ResponseEntity.ok().build();
    }



}
