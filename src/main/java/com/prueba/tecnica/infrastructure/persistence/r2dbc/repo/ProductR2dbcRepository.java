package com.prueba.tecnica.infrastructure.persistence.r2dbc.repo;

import com.prueba.tecnica.infrastructure.persistence.r2dbc.entity.MaxStockRow;
import com.prueba.tecnica.infrastructure.persistence.r2dbc.entity.ProductEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface ProductR2dbcRepository extends ReactiveCrudRepository<ProductEntity, String> {
    Flux<ProductEntity> findByBranchId(String branchId);

    @Query("""
    SELECT
      p.id   AS product_id,
      p.name AS product_name,
      p.stock AS product_stock,
      b.id   AS branch_id,
      b.name AS branch_name
    FROM (
      SELECT DISTINCT ON (p.branch_id)
        p.id, p.name, p.stock, p.branch_id
      FROM product p
      JOIN branch b ON b.id = p.branch_id
      WHERE b.franchise_id = :franchiseId
      ORDER BY p.branch_id, p.stock DESC, p.id
    ) p
    JOIN branch b ON b.id = p.branch_id
    ORDER BY b.name
    """)
    Flux<MaxStockRow> findMaxStockByFranchise(UUID franchiseId);
}
