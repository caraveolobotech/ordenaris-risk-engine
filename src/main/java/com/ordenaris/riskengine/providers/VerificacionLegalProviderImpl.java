package com.ordenaris.riskengine.providers;

import org.springframework.stereotype.Component;

@Component
public class VerificacionLegalProviderImpl implements VerificacionLegalProvider {

    @Override
    public boolean tieneJuicio(String empresaId) {
        return false;
    }
}