package com.prueba.tecnica.interfaces.rest.product.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RenameProductRequest {
    @NotBlank(message = "El nuevo nombre del producto no puede estar vacío")
    private String newName;
}
