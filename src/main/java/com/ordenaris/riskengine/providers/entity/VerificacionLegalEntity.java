package com.ordenaris.riskengine.providers.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "verificacion_legal")
public class VerificacionLegalEntity {
    @Id
    private String empresaId;
    private boolean juicioMercantilAbierto;

    // getters y setters
    public String getEmpresaId() { return empresaId; }
    public void setEmpresaId(String empresaId) { this.empresaId = empresaId; }
    public boolean isJuicioMercantilAbierto() { return juicioMercantilAbierto; }
    public void setJuicioMercantilAbierto(boolean juicioMercantilAbierto) { this.juicioMercantilAbierto = juicioMercantilAbierto; }
}