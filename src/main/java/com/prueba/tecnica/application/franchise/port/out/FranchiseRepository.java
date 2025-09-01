package com.prueba.tecnica.application.franchise.port.out;

import com.prueba.tecnica.domain.franchise.Franchise;
import com.prueba.tecnica.domain.franchise.FranchiseId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FranchiseRepository {

    Mono<Franchise> save(Franchise franchise);
    Mono<Franchise> findById(FranchiseId id);
    Mono<Boolean> existsById(FranchiseId id);

    Flux<Franchise> findAll();
}

