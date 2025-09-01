package com.prueba.tecnica.application.franchise.usecase;

import com.prueba.tecnica.domain.franchise.Franchise;
import com.prueba.tecnica.domain.franchise.FranchiseId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FranchiseUseCase {
    Mono<Franchise> create(String name);

    Mono<Franchise> rename(FranchiseId franchiseId, String newName);

    Flux<Franchise> findAll();

    // Flux<MaxStockItem> getMaxStockByBranch(FranchiseId franchiseId);

    Flux<MaxStockItem> handle(FranchiseId franchiseId);
}
