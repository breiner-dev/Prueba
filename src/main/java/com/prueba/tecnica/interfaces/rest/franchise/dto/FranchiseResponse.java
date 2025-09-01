package com.prueba.tecnica.interfaces.rest.franchise.dto;

import com.prueba.tecnica.domain.franchise.Franchise;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FranchiseResponse {
    private String id;
    private String name;

    public static FranchiseResponse from(Franchise f) {
        return new FranchiseResponse(f.id().value(), f.name());
    }
}
