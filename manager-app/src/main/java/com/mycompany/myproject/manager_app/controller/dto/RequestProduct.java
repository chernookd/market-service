package com.mycompany.myproject.manager_app.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RequestProduct {


    private Integer id;

    @NotNull
    private String name;

    @Size(min = 10, max = 2000)
    private String description;
}
