package com.ordenaris.riskengine.engine;

import java.math.BigDecimal;

import com.ordenaris.riskengine.enums.ProductoFinanciero;

import lombok.Data;

@Data
public class RuleContext {
    public BigDecimal promedioVentas;
    public boolean deudaVencidaMayor90Dias;
    public boolean juicioMercantilAbierto;
    public boolean historialExcelente;
    public int edadMeses;
    public ProductoFinanciero productoFinanciero;
    public BigDecimal montoSolicitado;
}