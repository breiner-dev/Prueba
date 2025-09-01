package com.prueba.tecnica.interfaces.rest.branch.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RenameBranchRequest {
    @NotBlank(message = "El nuevo nombre de la sucursal no puede estar vacío")
    private String newName;
}
