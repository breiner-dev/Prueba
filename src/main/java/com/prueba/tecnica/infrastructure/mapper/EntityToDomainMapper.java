package com.prueba.tecnica.infrastructure.mapper;

import com.prueba.tecnica.domain.branch.Branch;
import com.prueba.tecnica.domain.branch.BranchId;
import com.prueba.tecnica.domain.franchise.Franchise;
import com.prueba.tecnica.domain.franchise.FranchiseId;
import com.prueba.tecnica.domain.product.Product;
import com.prueba.tecnica.domain.product.ProductId;
import com.prueba.tecnica.domain.product.Stock;
import com.prueba.tecnica.infrastructure.persistence.r2dbc.entity.BranchEntity;
import com.prueba.tecnica.infrastructure.persistence.r2dbc.entity.FranchiseEntity;
import com.prueba.tecnica.infrastructure.persistence.r2dbc.entity.ProductEntity;
import org.springframework.stereotype.Component;

@Component
public class EntityToDomainMapper {

    public Franchise franchise(FranchiseEntity e) {
        if (e == null) return null;
        return new Franchise(
                new FranchiseId(e.id().toString()),
                e.name()
        );
    }

    public Branch branch(BranchEntity e) {
        if (e == null) return null;
        return new Branch(
                new BranchId(e.id().toString()),
                new FranchiseId(e.franchiseId().toString()),
                e.name()
        );
    }

    public Product product(ProductEntity e) {
        if (e == null) return null;
        return new Product(
                new ProductId(e.id().toString()),
                new BranchId(e.branchId().toString()),
                e.name(),
                new Stock(e.stock() == null ? 0 : e.stock())
        );
    }
}
