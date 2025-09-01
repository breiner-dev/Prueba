package com.prueba.tecnica.application.branch.service;

import com.prueba.tecnica.application.branch.port.out.BranchRepository;
import com.prueba.tecnica.application.branch.usecase.BranchUseCase;
import com.prueba.tecnica.domain.branch.Branch;
import com.prueba.tecnica.domain.branch.BranchId;
import com.prueba.tecnica.domain.franchise.FranchiseId;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BranchService implements BranchUseCase {

    private final BranchRepository branchRepository;

    @Override
    public Mono<Branch> add(FranchiseId franchiseId, String name) {
        Branch branch = new Branch(null, franchiseId, name);
        return branchRepository.save(branch);
    }

    @Override
    public Mono<Branch> rename(FranchiseId franchiseId, BranchId branchId, String newName) {
        return branchRepository.findById(branchId)
                .flatMap(existing -> {
                    Branch updated = new Branch(existing.id(), existing.franchiseId(), newName);
                    return branchRepository.save(updated);
                });
    }

    @Override
    public Flux<Branch> findByFranchiseId(FranchiseId fid) {
        return branchRepository.findByFranchiseId(fid);
    }
}
