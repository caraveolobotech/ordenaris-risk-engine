package com.ordenaris.riskengine.providers;

public interface HistorialPagosProvider {
    boolean tieneDeudaVencida(String empresaId);
    boolean historialExcelente(String empresaId);
}