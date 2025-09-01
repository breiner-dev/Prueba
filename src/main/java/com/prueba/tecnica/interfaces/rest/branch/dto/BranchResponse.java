package com.prueba.tecnica.interfaces.rest.branch.dto;

import com.prueba.tecnica.domain.branch.Branch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BranchResponse {
    private String id;
    private String franchiseId;
    private String name;

    public static BranchResponse from(Branch b) {
        return new BranchResponse(
                b.id().value(),
                b.franchiseId().value(),
                b.name()
        );
    }
}
