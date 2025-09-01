package com.prueba.tecnica.infrastructure.persistence.r2dbc.adapter;

import com.prueba.tecnica.application.branch.port.out.BranchRepository;
import com.prueba.tecnica.domain.branch.Branch;
import com.prueba.tecnica.domain.branch.BranchId;
import com.prueba.tecnica.domain.franchise.FranchiseId;
import com.prueba.tecnica.infrastructure.persistence.r2dbc.entity.BranchEntity;
import com.prueba.tecnica.infrastructure.persistence.r2dbc.repo.BranchR2dbcRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BranchRepositoryAdapter implements BranchRepository {

    private final BranchR2dbcRepository repo;

    @Override
    public Mono<Branch> save(Branch branch) {
        var id = (branch.id() != null && branch.id().value() != null) ? UUID.fromString(branch.id().value()) : null;
        BranchEntity entity = new BranchEntity(id, UUID.fromString(branch.franchiseId().value()), branch.name());
        return repo.save(entity).map(item -> new Branch(new BranchId(item.id().toString()), new FranchiseId(item.franchiseId().toString()), item.name()));
    }

    @Override
    public Mono<Branch> findById(BranchId id) {
        if (id == null || id.value() == null) new RuntimeException("Vacio");
        return repo.findById(UUID.fromString(id.value())).map(item -> new Branch(new BranchId(item.id().toString()), new FranchiseId(item.franchiseId().toString()), item.name()));
    }

    @Override
    public Flux<Branch> findByFranchiseId(FranchiseId franchiseId) {
        return repo.findByFranchiseId(UUID.fromString(franchiseId.value())).map(item -> new Branch(new BranchId(item.id().toString()), new FranchiseId(item.franchiseId().toString()), item.name()));
    }

    @Override
    public Mono<Boolean> existsByIdAndFranchiseId(BranchId branchId, FranchiseId franchiseId) {
        return repo.existsByIdAndFranchiseId(branchId.value(), franchiseId.value());
    }
}

