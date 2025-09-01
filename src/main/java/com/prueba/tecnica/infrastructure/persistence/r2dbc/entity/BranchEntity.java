package com.prueba.tecnica.infrastructure.persistence.r2dbc.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("branch")
public record BranchEntity(@Id UUID id, UUID franchiseId, String name) { }
