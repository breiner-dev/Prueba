package com.prueba.tecnica.interfaces.rest.product;

import com.prueba.tecnica.application.product.usecase.ProductUseCase;
import com.prueba.tecnica.domain.branch.BranchId;
import com.prueba.tecnica.domain.franchise.FranchiseId;
import com.prueba.tecnica.domain.product.ProductId;
import com.prueba.tecnica.domain.product.Stock;
import com.prueba.tecnica.interfaces.rest.product.dto.AddProductRequest;
import com.prueba.tecnica.interfaces.rest.product.dto.ProductResponse;
import com.prueba.tecnica.interfaces.rest.product.dto.RenameProductRequest;
import com.prueba.tecnica.interfaces.rest.product.dto.UpdateProductStockRequest;
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
@RequestMapping("/franchises/{fid}/branches/{bid}/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "Endpoints para gestionar productos de una sucursal")
public class ProductController {

    private final ProductUseCase productUseCase;

    @Operation(
            summary = "Crear producto",
            description = "Crea un producto en la sucursal `bid` de la franquicia `fid`."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Producto creado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponse.class),
                            examples = @ExampleObject(
                                    value = """
                        {"id":"6ec73df8-be21-4584-8316-7719bf4e2f75","name":"Café Latte","stock":25}
                        """
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "404", description = "Franquicia o sucursal no encontrada")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ProductResponse> add(
            @Parameter(description = "ID de la franquicia (UUID)", example = "80556265-cd7e-40a7-a081-90ef8ddf3877")
            @PathVariable("fid") String fid,
            @Parameter(description = "ID de la sucursal (UUID)", example = "5921a930-d7d3-4375-ad52-ecddd60c1447")
            @PathVariable("bid") String bid,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Datos del producto a crear",
                    content = @Content(
                            schema = @Schema(implementation = AddProductRequest.class),
                            examples = @ExampleObject(value = """
                                    {"name":"Café Latte","stock":25}""")
                    )
            )
            @Valid @RequestBody AddProductRequest req) {

        return productUseCase.add(new FranchiseId(fid), new BranchId(bid), req.getName(), new Stock(req.getStock()))
                .map(ProductResponse::from);
    }

    @Operation(
            summary = "Actualizar stock de un producto",
            description = "Actualiza el stock del producto `pid`."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Producto actualizado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponse.class),
                            examples = @ExampleObject(
                                    value = """
                        {"id":"6ec73df8-be21-4584-8316-7719bf4e2f75","name":"Café Latte","stock":40}
                        """
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
    })
    @PatchMapping("/{pid}/stock")
    public Mono<ProductResponse> updateStock(
            @Parameter(description = "ID de la franquicia (UUID)")
            @PathVariable("fid") String fid,
            @Parameter(description = "ID de la sucursal (UUID)")
            @PathVariable("bid") String bid,
            @Parameter(description = "ID del producto (UUID)")
            @PathVariable("pid") String pid,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Nuevo stock",
                    content = @Content(
                            schema = @Schema(implementation = UpdateProductStockRequest.class),
                            examples = @ExampleObject(value = """
                                    {"stock":40}""")
                    )
            )
            @Valid @RequestBody UpdateProductStockRequest req) {

        return productUseCase.update(new FranchiseId(fid), new BranchId(bid), new ProductId(pid), new Stock(req.getStock()))
                .map(ProductResponse::from);
    }

    @Operation(
            summary = "Listar productos por sucursal",
            description = "Obtiene todos los productos de la sucursal `bid`."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Listado de productos",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = ProductResponse.class)),
                    examples = @ExampleObject(
                            value = """
                    [
                      {"id":"a1","name":"Café","stock":10},
                      {"id":"b2","name":"Té","stock":5}
                    ]
                    """
                    )
            )
    )
    @GetMapping
    public Flux<ProductResponse> findAll(
            @Parameter(description = "ID de la franquicia (UUID)")
            @PathVariable("fid") String fid,
            @Parameter(description = "ID de la sucursal (UUID)")
            @PathVariable("bid") String bid) {

        return productUseCase.findByBranchId(new BranchId(bid))
                .map(ProductResponse::from);
    }

    @Operation(
            summary = "Renombrar producto",
            description = "Cambia el nombre del producto `pid`."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Producto renombrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponse.class),
                            examples = @ExampleObject(
                                    value = """
                        {"id":"6ec73df8-be21-4584-8316-7719bf4e2f75","name":"Café Latte Grande","stock":40}
                        """
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
    })
    @PatchMapping("/{pid}")
    public Mono<ProductResponse> rename(
            @Parameter(description = "ID de la franquicia (UUID)")
            @PathVariable("fid") String fid,
            @Parameter(description = "ID de la sucursal (UUID)")
            @PathVariable("bid") String bid,
            @Parameter(description = "ID del producto (UUID)")
            @PathVariable("pid") String pid,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "Nuevo nombre del producto",
                    content = @Content(
                            schema = @Schema(implementation = RenameProductRequest.class),
                            examples = @ExampleObject(value = """
                                    {"newName":"Café Latte Grande"}""")
                    )
            )
            @Valid @RequestBody RenameProductRequest req) {

        return productUseCase.rename(new FranchiseId(fid), new BranchId(bid), new ProductId(pid), req.getNewName())
                .map(ProductResponse::from);
    }

    @Operation(
            summary = "Eliminar producto",
            description = "Elimina el producto `pid` de la sucursal `bid`."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Eliminado"),
            @ApiResponse(responseCode = "404", description = "Recurso no encontrado")
    })
    @DeleteMapping("/{pid}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> remove(
            @Parameter(description = "ID de la franquicia (UUID)")
            @PathVariable("fid") String fid,
            @Parameter(description = "ID de la sucursal (UUID)")
            @PathVariable("bid") String bid,
            @Parameter(description = "ID del producto (UUID)")
            @PathVariable("pid") String pid) {

        return productUseCase.remove(new FranchiseId(fid), new BranchId(bid), new ProductId(pid));
    }
}
