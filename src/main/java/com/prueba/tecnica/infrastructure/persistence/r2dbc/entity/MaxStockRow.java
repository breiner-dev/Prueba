package com.prueba.tecnica.infrastructure.persistence.r2dbc.entity;

import java.util.UUID;

public record MaxStockRow(
        UUID branchId,
        String branchName,
        UUID productId,
        String productName,
        Integer productStock
) {}
