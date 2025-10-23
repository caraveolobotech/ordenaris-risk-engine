package com.ordenaris.riskengine.engine.rules;

import com.ordenaris.riskengine.dto.EvaluationRequestDto;
import com.ordenaris.riskengine.dto.RuleEvaluationDto;
import com.ordenaris.riskengine.engine.RuleContext;

public interface Rule {
    /**
     * Evalúa la regla sobre la solicitud.
     * @param request datos de la empresa solicitante
     * @return RuleEvaluationDto con resultado de la regla
     */
    RuleEvaluationDto evaluate(EvaluationRequestDto request, RuleContext context);

    /**
     * Retorna el nombre de la regla
     */
    String getName();
}