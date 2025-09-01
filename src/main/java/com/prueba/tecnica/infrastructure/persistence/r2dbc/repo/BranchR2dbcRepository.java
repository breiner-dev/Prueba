package com.prueba.tecnica.infrastructure.persistence.r2dbc.repo;


import com.prueba.tecnica.infrastructure.persistence.r2dbc.entity.BranchEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface BranchR2dbcRepository extends ReactiveCrudRepository<BranchEntity, UUID> {
    Flux<BranchEntity> findByFranchiseId(UUID franchiseId);
    Mono<Boolean> existsByIdAndFranchiseId(String id, String franchiseId);
}

