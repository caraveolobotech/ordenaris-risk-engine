package com.ordenaris.riskengine.providers;

import org.springframework.stereotype.Component;

@Component
public class HistorialPagosProviderImpl implements HistorialPagosProvider {

    @Override
    public boolean tieneDeudaVencida(String empresaId) {
        return false;
    }

    @Override
    public boolean historialExcelente(String empresaId) {
        return true;
    }
}