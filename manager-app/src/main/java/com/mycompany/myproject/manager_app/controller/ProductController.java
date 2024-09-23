package com.mycompany.myproject.manager_app.controller;


import com.mycompany.myproject.manager_app.client.ProductRestClient;
import com.mycompany.myproject.manager_app.controller.dto.RequestProduct;
import com.mycompany.myproject.manager_app.dto.Product;
import com.mycompany.myproject.manager_app.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Locale;
import java.util.NoSuchElementException;

@Controller
@RequestMapping(("catalogue/products/{productId:\\d+}"))
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    public final ProductRestClient productRestClient;

    @Autowired
    public ProductController(ProductRestClient productRestClient) {
        this.productRestClient = productRestClient;
    }

    @ModelAttribute(value = "product", binding = false)
    public Product product(@PathVariable("productId") int productId) {
        return this.productRestClient.findProduct(productId).get();
    }


    @GetMapping
    public String getProduct(@PathVariable(value = "productId") int productId, Model model) {
        return "catalogue/products/product";
    }

    @GetMapping("edit")
    public String getProductEditPage(@PathVariable(value = "productId") int productId, Model model) {
        logger.info("getProductEditPage");

        return "catalogue/products/edit";
    }

    @PostMapping("edit")
    public String UpdateProduct(@ModelAttribute(name = "product", binding = false) Product product,
                                @Valid RequestProduct requestProduct, Model model) {

        logger.info("UpdateProduct start");



        Product updateProduct = productRestClient
                .updateProduct(product.getId(), requestProduct.getName(), requestProduct.getDescription());

        logger.info("UpdateProduct stop");


        return "redirect:/catalogue/products/%d".formatted(updateProduct.getId());
    }

    @PostMapping("/delete")
    public String DeleteProduct(@ModelAttribute(name = "product", binding = false) Product product) {
        productRestClient.deleteProduct(product.getId());

        return "redirect:/catalogue/products/list";
    }


    @ExceptionHandler(NoSuchElementException.class)
    public String noSuchElementExceptionHandler(NoSuchElementException e, Model model, Locale locale) {
        model.addAttribute("error", e.getMessage());

        return "errors/404";
    }


}
