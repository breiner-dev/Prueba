package com.prueba.tecnica.interfaces.rest.product.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class UpdateProductStockRequest {
    @Min(value = 0, message = "El stock no puede ser negativo")
    private int stock;
}
