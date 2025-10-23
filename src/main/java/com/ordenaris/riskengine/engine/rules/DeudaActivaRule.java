package com.ordenaris.riskengine.engine.rules;

import com.ordenaris.riskengine.dto.EvaluationRequestDto;
import com.ordenaris.riskengine.dto.RuleEvaluationDto;
import com.ordenaris.riskengine.engine.RuleContext;

public class DeudaActivaRule implements Rule {

    @Override
    public RuleEvaluationDto evaluate(EvaluationRequestDto request, RuleContext context) {
        RuleEvaluationDto result = new RuleEvaluationDto();
        result.setNombreRegla(getName());
        if (context.deudaVencidaMayor90Dias) {
            result.setResultado("RECHAZADO");
        } else {
            result.setResultado("OK");
        }
        return result;
    }

    @Override
    public String getName() {
        return "Deuda Activa";
    }
}