package com.prueba.tecnica.domain.product;

import com.prueba.tecnica.domain.branch.BranchId;

public record Product(ProductId id, BranchId branchId, String name, Stock stock) {}
