package com.prueba.tecnica.infrastructure.persistence.r2dbc.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;


@Table("product")
public record ProductEntity (@Id UUID id, UUID branchId, String name, Integer stock) {}