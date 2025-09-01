package com.prueba.tecnica.infrastructure.mapper;

import com.prueba.tecnica.domain.branch.Branch;
import com.prueba.tecnica.domain.product.Product;
import com.prueba.tecnica.domain.franchise.Franchise;
import com.prueba.tecnica.infrastructure.persistence.r2dbc.entity.BranchEntity;
import com.prueba.tecnica.infrastructure.persistence.r2dbc.entity.FranchiseEntity;
import com.prueba.tecnica.infrastructure.persistence.r2dbc.entity.ProductEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DomainToEntityMapper {

    public FranchiseEntity franchise(Franchise f) {
        if (f == null) return null;
        return new FranchiseEntity(
                (f.id() != null && f.id().value() != null) ? UUID.fromString(f.id().value()) : null,
                f.name()
        );
    }

    public BranchEntity branch(Branch b) {
        if (b == null) return null;
        return new BranchEntity(
                UUID.fromString(b.id().value()),
                UUID.fromString(b.franchiseId().value()),
                b.name()
        );
    }

    public ProductEntity product(Product p) {
        if (p == null) return null;
        return new ProductEntity(
                (p.id() != null && p.id().value() != null) ? UUID.fromString(p.id().value()) : null,
                UUID.fromString(p.branchId().value()),
                p.name(),
                p.stock().value()
        );
    }
}
