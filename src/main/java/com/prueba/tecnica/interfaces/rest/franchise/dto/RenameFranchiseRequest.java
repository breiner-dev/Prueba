package com.prueba.tecnica.interfaces.rest.franchise.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RenameFranchiseRequest {

    @NotBlank(message = "El nuevo nombre no puede estar vacío")
    private String newName;
}
