package com.prueba.tecnica.application.branch.port.out;

import com.prueba.tecnica.domain.branch.Branch;
import com.prueba.tecnica.domain.branch.BranchId;
import com.prueba.tecnica.domain.franchise.FranchiseId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BranchRepository {
    Mono<Branch> save(Branch branch);
    Mono<Branch> findById(BranchId branchId);
    Flux<Branch> findByFranchiseId(FranchiseId franchiseId);
    Mono<Boolean> existsByIdAndFranchiseId(BranchId branchId, FranchiseId franchiseId);
}