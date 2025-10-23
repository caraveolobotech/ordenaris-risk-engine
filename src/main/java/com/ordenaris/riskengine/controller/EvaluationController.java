package com.ordenaris.riskengine.controller;

import com.ordenaris.riskengine.dto.EvaluationRequestDto;
import com.ordenaris.riskengine.dto.EvaluationResultDto;
import com.ordenaris.riskengine.service.EvaluationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "Evaluación de Riesgo", description = "Servicio que evalúa el nivel de riesgo de una solicitud usando reglas de negocio de Ordenaris")
public class EvaluationController {

    private final EvaluationService service;

    public EvaluationController(EvaluationService service) {
        this.service = service;
    }

    @Operation(
        summary = "Evalúa el riesgo de una solicitud",
        description = "Recibe los datos de la empresa solicitante y devuelve el nivel de riesgo junto con el detalle de las reglas aplicadas."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Evaluación completada exitosamente",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = EvaluationResultDto.class))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Solicitud inválida (parámetros incorrectos)",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Error interno del servidor",
            content = @Content
        )
    })
    @PostMapping("/evaluate")
    public EvaluationResultDto evaluar(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos de entrada para la evaluación de riesgo",
                required = true,
                content = @Content(schema = @Schema(implementation = EvaluationRequestDto.class))
            )
            @RequestBody EvaluationRequestDto request) {

        return service.evaluar(request);
    }
}