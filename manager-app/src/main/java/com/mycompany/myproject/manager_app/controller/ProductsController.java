package com.mycompany.myproject.manager_app.controller;

import com.mycompany.myproject.manager_app.client.ProductRestClient;
import com.mycompany.myproject.manager_app.controller.dto.RequestProduct;
import com.mycompany.myproject.manager_app.dto.Product;
import com.mycompany.myproject.manager_app.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("catalogue/products")
public class ProductsController {



    private final ProductRestClient productRestClient;

    @Autowired
    public ProductsController(ProductRestClient productRestClient) {
        this.productRestClient = productRestClient;
    }



    @GetMapping("list")
    public String getProductsList(Model model) {
        model.addAttribute("products", this.productRestClient.findAllProduct());

        return "catalogue/products/list";
    }

    @GetMapping(value = "list", params = "filter")
    public String getProductsListWithFilter(Model model, @RequestParam(name = "filter") String filter) {
        model.addAttribute("products", this.productRestClient.findAllProductWithFilter(filter));
        return "catalogue/products/list";
    }


    @GetMapping("create")
    public String getNewProductPage() {
        return "catalogue/products/new_product";
    }

    @PostMapping("create")
    public String createProduct(@Valid RequestProduct newProductDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("newProductDto", newProductDto);
            model.addAttribute("errors", bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList());

            return "catalogue/products/new_product";
        }

        Product product = productRestClient
                .createProduct(newProductDto.getName(), newProductDto.getDescription());

        return "redirect:/catalogue/products/%d".formatted(product.getId());
    }

}
