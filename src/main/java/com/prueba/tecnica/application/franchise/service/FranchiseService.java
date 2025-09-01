package com.prueba.tecnica.application.franchise.service;

import com.prueba.tecnica.application.franchise.usecase.FranchiseUseCase;
import com.prueba.tecnica.application.franchise.usecase.MaxStockItem;
import com.prueba.tecnica.application.branch.port.out.BranchRepository;
import com.prueba.tecnica.application.product.port.out.ProductRepository;
import com.prueba.tecnica.domain.franchise.Franchise;
import com.prueba.tecnica.domain.franchise.FranchiseId;
import com.prueba.tecnica.application.franchise.port.out.FranchiseRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FranchiseService implements FranchiseUseCase {

    private final FranchiseRepository franchiseRepo;
    private final BranchRepository branchRepo;
    private final ProductRepository productRepo;

    @Override
    public Mono<Franchise> create(String name) {
        Franchise franchise = new Franchise(null, name);
        return franchiseRepo.save(franchise);
    }

    @Override
    public Mono<Franchise> rename(FranchiseId id, String newName) {
        return franchiseRepo.findById(id)
                .flatMap(f -> franchiseRepo.save(new Franchise(f.id(), newName)));
    }

    @Override
    public Flux<Franchise> findAll() {
        return franchiseRepo.findAll();
    }

    @Override
    public Flux<MaxStockItem> handle(FranchiseId id) {
        return productRepo.findMaxStockByFranchise(id)
                .map(r -> new MaxStockItem(
                        r.branchId().toString(),
                        r.branchName(),
                        r.productId().toString(),
                        r.productName(),
                        r.productStock()
                ));
    }
}
