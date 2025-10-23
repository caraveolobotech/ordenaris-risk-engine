package com.ordenaris.riskengine.engine.rules;

import java.math.BigDecimal;

import com.ordenaris.riskengine.dto.EvaluationRequestDto;
import com.ordenaris.riskengine.dto.RuleEvaluationDto;
import com.ordenaris.riskengine.engine.RuleContext;

public class AltaSolicitudVsVentasRule implements Rule {

    @Override
    public RuleEvaluationDto evaluate(EvaluationRequestDto request, RuleContext context) {
        RuleEvaluationDto result = new RuleEvaluationDto();
        result.setNombreRegla(getName());

        // Validar que los valores no sean nulos
        if (context.montoSolicitado == null || context.promedioVentas == null) {
            result.setResultado("ALTO"); // o "OK" según la lógica que quieras aplicar
            return result;
        }

        BigDecimal limite = context.promedioVentas.multiply(BigDecimal.valueOf(8));

        if (context.montoSolicitado.compareTo(limite) > 0) {
            result.setResultado("ALTO");
        } else {
            result.setResultado("OK");
        }

        return result;
    }

    @Override
    public String getName() {
        return "Alta Solicitud vs Ventas";
    }
}