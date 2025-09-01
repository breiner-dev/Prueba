package com.prueba.tecnica.interfaces.rest.branch;

import com.prueba.tecnica.application.branch.usecase.BranchUseCase;
import com.prueba.tecnica.domain.branch.BranchId;
import com.prueba.tecnica.domain.franchise.FranchiseId;
import com.prueba.tecnica.interfaces.rest.branch.dto.AddBranchRequest;
import com.prueba.tecnica.interfaces.rest.branch.dto.BranchResponse;
import com.prueba.tecnica.interfaces.rest.branch.dto.RenameBranchRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/franchises/{fid}/branches")
@Tag(name = "Branches", description = "Endpoints para gestionar sucursales de una franquicia")
public class BranchController {

    private final BranchUseCase branchCase;

    @Operation(
            summary = "Crear sucursal",
            description = "Crea una nueva sucursal dentro de la franquicia indicada por `fid`."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Sucursal creada",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BranchResponse.class),
                            examples = @ExampleObject(
                                    name = "created",
                                    value = """
                        {"id":"5921a930-d7d3-4375-ad52-ecddd60c1447","name":"Sucursal Centro"}
                        """
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida (validación)"),
            @ApiResponse(responseCode = "404", description = "Franquicia no encontrada")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BranchResponse> addBranch(
            @Parameter(description = "ID de la franquicia (UUID)", example = "80556265-cd7e-40a7-a081-90ef8ddf3877")
            @PathVariable String fid,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Payload con el nombre de la sucursal",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AddBranchRequest.class),
                            examples = @ExampleObject(value = """
                                    {"name":"Sucursal Centro"}
                                    """)
                    )
            )
            @Valid @RequestBody AddBranchRequest req) {

        return branchCase.add(new FranchiseId(fid), req.getName())
                .map(BranchResponse::from);
    }

    @Operation(
            summary = "Listar sucursales por franquicia",
            description = "Devuelve todas las sucursales pertenecientes a la franquicia `fid`."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de sucursales",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = BranchResponse.class)),
                            examples = @ExampleObject(
                                    name = "ok",
                                    value = """
                        [
                          {"id":"5921a930-d7d3-4375-ad52-ecddd60c1447","name":"Sucursal Centro"},
                          {"id":"8d8adf3a-3a9a-4c86-9c6c-0ac7e42a5a26","name":"Sucursal Norte"}
                        ]
                        """
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Franquicia no encontrada")
    })
    @GetMapping
    public Flux<BranchResponse> findAll(
            @Parameter(description = "ID de la franquicia (UUID)", example = "80556265-cd7e-40a7-a081-90ef8ddf3877")
            @PathVariable String fid) {

        return branchCase.findByFranchiseId(new FranchiseId(fid))
                .map(BranchResponse::from);
    }

    @Operation(
            summary = "Renombrar sucursal",
            description = "Actualiza el nombre de la sucursal `bid` perteneciente a la franquicia `fid`."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Sucursal renombrada",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = BranchResponse.class),
                            examples = @ExampleObject(
                                    name = "ok",
                                    value = """
                        {"id":"5921a930-d7d3-4375-ad52-ecddd60c1447","name":"Sucursal Renovada"}
                        """
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida (validación)"),
            @ApiResponse(responseCode = "404", description = "Franquicia o sucursal no encontrada")
    })
    @PatchMapping("/{bid}")
    public Mono<BranchResponse> rename(
            @Parameter(description = "ID de la franquicia (UUID)", example = "80556265-cd7e-40a7-a081-90ef8ddf3877")
            @PathVariable String fid,
            @Parameter(description = "ID de la sucursal (UUID)", example = "5921a930-d7d3-4375-ad52-ecddd60c1447")
            @PathVariable String bid,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Payload con el nuevo nombre",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = RenameBranchRequest.class),
                            examples = @ExampleObject(value = """
                                    {"newName":"Sucursal Renovada"}
                                    """)
                    )
            )
            @Valid @RequestBody RenameBranchRequest req) {

        return branchCase.rename(new FranchiseId(fid), new BranchId(bid), req.getNewName())
                .map(BranchResponse::from);
    }
}
