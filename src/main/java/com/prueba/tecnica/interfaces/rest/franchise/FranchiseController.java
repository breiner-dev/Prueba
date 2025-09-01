package com.prueba.tecnica.interfaces.rest.franchise;

import com.prueba.tecnica.application.franchise.usecase.FranchiseUseCase;
import com.prueba.tecnica.domain.franchise.FranchiseId;
import com.prueba.tecnica.interfaces.rest.franchise.dto.CreateFranchiseRequest;
import com.prueba.tecnica.interfaces.rest.franchise.dto.FranchiseResponse;
import com.prueba.tecnica.interfaces.rest.franchise.dto.MaxStockResponse;
import com.prueba.tecnica.interfaces.rest.franchise.dto.RenameFranchiseRequest;
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
@RequestMapping("/franchises")
@Tag(name = "Franchises", description = "Endpoints para gestionar franquicias")
public class FranchiseController {

    private final FranchiseUseCase createFranchise;

    @Operation(
            summary = "Crear franquicia",
            description = "Crea una nueva franquicia."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Franquicia creada",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FranchiseResponse.class),
                            examples = @ExampleObject(
                                    value = """
                        {"id":"80556265-cd7e-40a7-a081-90ef8ddf3877","name":"Coffee Corp"}
                        """
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<FranchiseResponse> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Datos para crear la franquicia",
                    content = @Content(
                            schema = @Schema(implementation = CreateFranchiseRequest.class),
                            examples = @ExampleObject(value = """
                                    {"name":"Coffee Corp"}""")
                    )
            )
            @Valid @RequestBody CreateFranchiseRequest req
    ) {
        return createFranchise.create(req.getName())
                .map(FranchiseResponse::from);
    }

    @Operation(
            summary = "Listar franquicias",
            description = "Devuelve todas las franquicias."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Listado de franquicias",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = FranchiseResponse.class)),
                    examples = @ExampleObject(
                            value = """
                    [
                      {"id":"80556265-cd7e-40a7-a081-90ef8ddf3877","name":"Coffee Corp"},
                      {"id":"a5f6f35c-6a87-4f6e-9b25-3fb5a1b4e3d1","name":"Tea House"}
                    ]
                    """
                    )
            )
    )
    @GetMapping
    public Flux<FranchiseResponse> findAll() {
        return createFranchise.findAll()
                .map(FranchiseResponse::from);
    }

    @Operation(
            summary = "Renombrar franquicia",
            description = "Cambia el nombre de la franquicia indicada por `fid`."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Franquicia renombrada",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = FranchiseResponse.class),
                            examples = @ExampleObject(
                                    value = """
                        {"id":"80556265-cd7e-40a7-a081-90ef8ddf3877","name":"Coffee Corp Intl"}
                        """
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "404", description = "Franquicia no encontrada")
    })
    @PatchMapping("/{fid}")
    public Mono<FranchiseResponse> rename(
            @Parameter(description = "ID de la franquicia (UUID)", example = "80556265-cd7e-40a7-a081-90ef8ddf3877")
            @PathVariable String fid,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Nuevo nombre de la franquicia",
                    content = @Content(
                            schema = @Schema(implementation = RenameFranchiseRequest.class),
                            examples = @ExampleObject(value = """
                                    {"newName":"Coffee Corp Intl"}""")
                    )
            )
            @Valid @RequestBody RenameFranchiseRequest req
    ) {
        return createFranchise.rename(new FranchiseId(fid), req.getNewName())
                .map(FranchiseResponse::from);
    }

    @Operation(
            summary = "Producto con más stock por sucursal",
            description = "Para la franquicia `fid`, devuelve el producto con mayor stock en cada sucursal perteneciente a esa franquicia."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Listado de máximos por sucursal",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = MaxStockResponse.class)),
                            examples = @ExampleObject(
                                    value = """
                        [
                          {
                            "branchId":"5921a930-d7d3-4375-ad52-ecddd60c1447",
                            "branchName":"Sucursal Centro",
                            "productId":"6ec73df8-be21-4584-8316-7719bf4e2f75",
                            "productName":"Café Latte",
                            "productStock":40
                          },
                          {
                            "branchId":"3c0d9a74-4ef5-4b3d-98bb-3f3c0b2cd2a1",
                            "branchName":"Sucursal Norte",
                            "productId":"9a8e7f6d-5c4b-3a2b-1c0d-efab01234567",
                            "productName":"Té Verde",
                            "productStock":32
                          }
                        ]
                        """
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Franquicia no encontrada")
    })
    @GetMapping("/{fid}/max-stock-by-branch")
    public Flux<MaxStockResponse> maxStockByBranch(
            @Parameter(description = "ID de la franquicia (UUID)", example = "80556265-cd7e-40a7-a081-90ef8ddf3877")
            @PathVariable String fid
    ) {
        return createFranchise.handle(new FranchiseId(fid))
                .map(MaxStockResponse::from);
    }
}
