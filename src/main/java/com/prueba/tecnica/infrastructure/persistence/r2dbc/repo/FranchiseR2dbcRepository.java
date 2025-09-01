package com.prueba.tecnica.infrastructure.persistence.r2dbc.repo;

import com.prueba.tecnica.infrastructure.persistence.r2dbc.entity.FranchiseEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface FranchiseR2dbcRepository extends ReactiveCrudRepository<FranchiseEntity, UUID> { }

