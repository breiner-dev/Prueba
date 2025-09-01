package com.prueba.tecnica.application.product.usecase;

import com.prueba.tecnica.domain.branch.BranchId;
import com.prueba.tecnica.domain.franchise.FranchiseId;
import com.prueba.tecnica.domain.product.Product;
import com.prueba.tecnica.domain.product.ProductId;
import com.prueba.tecnica.domain.product.Stock;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductUseCase {
    Mono<Product> add(FranchiseId fid, BranchId bid, String name, Stock stock);
    Mono<Void> remove(FranchiseId franchiseId, BranchId branchId, ProductId productId);
    Mono<Product> update(FranchiseId fid, BranchId bid, ProductId pid, Stock stock);
    Flux<Product> findByBranchId(BranchId bid);
    Mono<Product> rename(FranchiseId fid, BranchId bid, ProductId pid, String newName);
}
