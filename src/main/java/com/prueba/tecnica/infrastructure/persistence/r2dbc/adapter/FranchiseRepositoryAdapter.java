package com.prueba.tecnica.infrastructure.persistence.r2dbc.adapter;

import com.prueba.tecnica.application.franchise.port.out.FranchiseRepository;
import com.prueba.tecnica.domain.franchise.Franchise;
import com.prueba.tecnica.domain.franchise.FranchiseId;

import com.prueba.tecnica.infrastructure.persistence.r2dbc.entity.FranchiseEntity;
import com.prueba.tecnica.infrastructure.persistence.r2dbc.repo.FranchiseR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FranchiseRepositoryAdapter implements FranchiseRepository {

    private final FranchiseR2dbcRepository repo;

    @Override
    public Mono<Franchise> save(Franchise franchise) {
        var id = (  franchise.id() != null && franchise.id().value() != null) ? UUID.fromString(franchise.id().value()) : null;
        FranchiseEntity entity = new FranchiseEntity(id, franchise.name());
        return repo.save(entity).map(item -> new Franchise(new FranchiseId(item.id().toString()), item.name()));
    }

    @Override
    public Mono<Franchise> findById(FranchiseId id) {
        if (id == null || id.value() == null) new RuntimeException("Id vacio");
        return repo.findById(UUID.fromString(id.value())).map(item -> new Franchise(new FranchiseId(item.id().toString()), item.name()));
    }

    @Override
    public Mono<Boolean> existsById(FranchiseId id) {
        return repo.existsById(UUID.fromString(id.value()));
    }

    @Override
    public Flux<Franchise> findAll() {
        return repo.findAll().map(item -> new Franchise(new FranchiseId(item.id().toString()), item.name()));
    }
}

