package com.prueba.tecnica.application.product.port.out;

import com.prueba.tecnica.domain.branch.BranchId;
import com.prueba.tecnica.domain.franchise.FranchiseId;
import com.prueba.tecnica.domain.product.Product;
import com.prueba.tecnica.domain.product.ProductId;
import com.prueba.tecnica.infrastructure.persistence.r2dbc.entity.MaxStockRow;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductRepository {
    Mono<Product> save(Product p);
    Mono<Void> deleteById(ProductId productId);
    Mono<Product> findById(ProductId productId);
    Flux<Product> findByBranchId(BranchId branchId);
    Flux<MaxStockRow> findMaxStockByFranchise(FranchiseId id);
}

