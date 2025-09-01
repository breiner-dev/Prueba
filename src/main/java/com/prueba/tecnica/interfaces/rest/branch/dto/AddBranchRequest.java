package com.prueba.tecnica.interfaces.rest.branch.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddBranchRequest {
    @NotBlank(message = "El nombre de la sucursal no puede estar vacío")
    private String name;
}
