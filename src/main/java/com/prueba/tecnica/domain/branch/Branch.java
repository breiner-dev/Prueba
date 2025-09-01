package com.prueba.tecnica.domain.branch;

import com.prueba.tecnica.domain.franchise.FranchiseId;

public record Branch(BranchId id, FranchiseId franchiseId, String name) {}
