package com.ordenaris.riskengine.providers.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "datos_contables")
public class DatosContablesEntity {
    @Id
    private String empresaId;
    private BigDecimal ventasPromedio;
    private BigDecimal pasivos;
    private BigDecimal activos;

    // getters y setters
    public String getEmpresaId() { return empresaId; }
    public void setEmpresaId(String empresaId) { this.empresaId = empresaId; }
    public BigDecimal getVentasPromedio() { return ventasPromedio; }
    public void setVentasPromedio(BigDecimal ventasPromedio) { this.ventasPromedio = ventasPromedio; }
    public BigDecimal getPasivos() { return pasivos; }
    public void setPasivos(BigDecimal pasivos) { this.pasivos = pasivos; }
    public BigDecimal getActivos() { return activos; }
    public void setActivos(BigDecimal activos) { this.activos = activos; }
}