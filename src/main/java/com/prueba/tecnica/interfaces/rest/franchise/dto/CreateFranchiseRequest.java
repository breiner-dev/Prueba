package com.prueba.tecnica.interfaces.rest.franchise.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateFranchiseRequest {

    @NotBlank(message = "El nombre de la franquicia no puede estar vacío")
    private String name;
}
