package com.ordenaris.riskengine.providers;

import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class DatosContablesProviderImpl implements DatosContablesProvider {

    @Override
    public BigDecimal getPromedioVentas(String empresaId) {
        // Aquí pones la lógica real o dummy
        return BigDecimal.valueOf(1000);
    }

    @Override
    public BigDecimal getPasivos(String empresaId) {
        return BigDecimal.valueOf(500);
    }

    @Override
    public BigDecimal getActivos(String empresaId) {
        return BigDecimal.valueOf(2000);
    }
}