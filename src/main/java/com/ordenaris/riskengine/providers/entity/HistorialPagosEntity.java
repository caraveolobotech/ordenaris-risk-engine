package com.ordenaris.riskengine.providers.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "historial_pagos")
public class HistorialPagosEntity {
    @Id
    private String empresaId;
    private boolean deudaVencida;
    private boolean historialExcelente;

    // getters y setters
    public String getEmpresaId() { return empresaId; }
    public void setEmpresaId(String empresaId) { this.empresaId = empresaId; }
    public boolean isDeudaVencida() { return deudaVencida; }
    public void setDeudaVencida(boolean deudaVencida) { this.deudaVencida = deudaVencida; }
    public boolean isHistorialExcelente() { return historialExcelente; }
    public void setHistorialExcelente(boolean historialExcelente) { this.historialExcelente = historialExcelente; }
}