package com.mycompany.myproject.manager_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Product {

    private Integer id;

    private String name;

    private String description;
}
