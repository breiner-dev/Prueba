package com.prueba.tecnica.infrastructure.persistence.r2dbc.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("franchise")
public record FranchiseEntity (@Id UUID id, String name){}
