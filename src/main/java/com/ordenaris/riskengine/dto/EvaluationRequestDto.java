package com.ordenaris.riskengine.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.ordenaris.riskengine.enums.ProductoFinanciero;

import lombok.Data;

@Data
public class EvaluationRequestDto {
    private String empresaId;
    private BigDecimal montoSolicitado;
    private ProductoFinanciero productoFinanciero;
    private LocalDate fechaSolicitud;
}