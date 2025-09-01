package com.prueba.tecnica.infrastructure.persistence.r2dbc.adapter;

import com.prueba.tecnica.application.product.port.out.ProductRepository;
import com.prueba.tecnica.domain.branch.BranchId;
import com.prueba.tecnica.domain.franchise.FranchiseId;
import com.prueba.tecnica.domain.product.Product;
import com.prueba.tecnica.domain.product.ProductId;
import com.prueba.tecnica.infrastructure.mapper.DomainToEntityMapper;
import com.prueba.tecnica.infrastructure.mapper.EntityToDomainMapper;
import com.prueba.tecnica.infrastructure.persistence.r2dbc.entity.MaxStockRow;
import com.prueba.tecnica.infrastructure.persistence.r2dbc.repo.ProductR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
class ProductRepositoryAdapter implements ProductRepository {

    private final ProductR2dbcRepository repo;
    private final EntityToDomainMapper toDomain;
    private final DomainToEntityMapper toEntity;

    @Override
    public Mono<Product> save(Product p) {
        return repo.save(toEntity.product(p)).map(toDomain::product);
    }

    @Override
    public Mono<Void> deleteById(ProductId productId) {
        return repo.deleteById(productId.value());
    }


    @Override
    public Mono<Product> findById(ProductId id) {
        return repo.findById(id.value()).map(toDomain::product);
    }

    @Override
    public Flux<Product> findByBranchId(BranchId branchId) {
        return repo.findByBranchId(branchId.value()).map(toDomain::product);
    }

    @Override
    public Flux<MaxStockRow> findMaxStockByFranchise(FranchiseId id) {
        return repo.findMaxStockByFranchise(UUID.fromString(id.value()) );
    }

}

