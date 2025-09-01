package com.prueba.tecnica.interfaces.rest.franchise.dto;

import com.prueba.tecnica.application.franchise.usecase.MaxStockItem;
import com.prueba.tecnica.domain.franchise.Franchise;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaxStockResponse {

    private String branchId;
    private String branchName;
    private String productId;
    private String productName;
    private int stock;

    public static MaxStockResponse from(MaxStockItem f) {
        return new MaxStockResponse(f.branchId(), f.branchName(), f.productId(), f.productName(), f.stock());
    }
}

