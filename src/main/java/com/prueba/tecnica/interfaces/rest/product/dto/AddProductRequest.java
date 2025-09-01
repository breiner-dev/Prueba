package com.prueba.tecnica.interfaces.rest.product.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddProductRequest {
    @NotBlank(message = "El nombre del producto no puede estar vacío")
    private String name;

    @Min(value = 0, message = "El stock no puede ser negativo")
    private int stock;
}