package com.example.catalogue_service.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class Product {
    private Integer id;

    private String name;

    private String description;

}
