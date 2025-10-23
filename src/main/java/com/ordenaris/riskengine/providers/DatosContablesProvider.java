package com.ordenaris.riskengine.providers;

import java.math.BigDecimal;

public interface DatosContablesProvider {
    BigDecimal getPromedioVentas(String empresaId);
    BigDecimal getPasivos(String empresaId);
    BigDecimal getActivos(String empresaId);
}