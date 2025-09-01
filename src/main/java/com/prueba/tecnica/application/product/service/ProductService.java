package com.prueba.tecnica.application.product.service;

import com.prueba.tecnica.application.branch.port.out.BranchRepository;
import com.prueba.tecnica.application.product.port.out.ProductRepository;
import com.prueba.tecnica.application.product.usecase.ProductUseCase;
import com.prueba.tecnica.domain.branch.BranchId;
import com.prueba.tecnica.domain.franchise.Franchise;
import com.prueba.tecnica.domain.franchise.FranchiseId;
import com.prueba.tecnica.domain.product.Product;
import com.prueba.tecnica.domain.product.ProductId;
import com.prueba.tecnica.domain.product.Stock;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductUseCase {

    private final ProductRepository productRepository;
    private final BranchRepository branchRepository;

    @Override
    public Mono<Product> add(FranchiseId fid, BranchId bid, String name, Stock stock) {
        return validateBranchBelongsToFranchise(bid, fid)
                .then(Mono.defer(() -> {
                    Product product = new Product(new ProductId(null), bid, name, stock);
                    return productRepository.save(product);
                }));
    }

    @Override
    public Flux<Product> findByBranchId(BranchId bid) {
        return productRepository.findByBranchId(bid);
    }

    @Override
    public Mono<Void> remove(FranchiseId fid, BranchId bid, ProductId pid) {
        return validateBranchBelongsToFranchise(bid, fid)
                .then(productRepository.findById(pid))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product not found: " + pid.value())))
                .flatMap(existing -> {
                    return productRepository.deleteById(pid);
                });
    }

    @Override
    public Mono<Product> update(FranchiseId fid, BranchId bid, ProductId pid, Stock stock) {
        return validateBranchBelongsToFranchise(bid, fid)
                .then(productRepository.findById(pid))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product not found: " + pid.value())))
                .flatMap(existing -> {
                    Product updated = new Product(existing.id(), existing.branchId(), existing.name(), stock);
                    return productRepository.save(updated);
                });
    }

    private Mono<Void> validateBranchBelongsToFranchise(BranchId bid, FranchiseId fid) {
        return branchRepository.existsByIdAndFranchiseId(bid, fid)
                .flatMap(exists -> exists
                        ? Mono.empty()
                        : Mono.error(new IllegalArgumentException(
                        "Branch " + bid.value() + " does not belong to franchise " + fid.value()
                )));
    }

    @Override
    public Mono<Product> rename(FranchiseId fid, BranchId bid, ProductId pid, String newName) {
        return validateBranchBelongsToFranchise(bid, fid)
                .then(productRepository.findById(pid))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Product not found: " + pid.value())))
                .flatMap(existing -> {
                    Product updated = new Product(existing.id(), existing.branchId(), newName, existing.stock());
                    return productRepository.save(updated);
                });
    }
}

