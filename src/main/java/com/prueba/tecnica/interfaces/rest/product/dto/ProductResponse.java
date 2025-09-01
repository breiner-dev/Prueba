package com.prueba.tecnica.interfaces.rest.product.dto;

import com.prueba.tecnica.domain.product.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private String id;
    private String branchId;
    private String name;
    private int stock;

    public static ProductResponse from(Product p) {
        return new ProductResponse(
                p.id().value(),
                p.branchId().value(),
                p.name(),
                p.stock().value()
        );
    }
}
