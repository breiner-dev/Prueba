package com.prueba.tecnica.application.branch.usecase;

import com.prueba.tecnica.domain.branch.Branch;
import com.prueba.tecnica.domain.branch.BranchId;
import com.prueba.tecnica.domain.franchise.FranchiseId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BranchUseCase {
    Mono<Branch> add(FranchiseId franchiseId, String name);
    Mono<Branch> rename(FranchiseId franchiseId, BranchId branchId, String newName);
    Flux<Branch> findByFranchiseId(FranchiseId fid);
}