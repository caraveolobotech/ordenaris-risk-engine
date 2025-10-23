package com.ordenaris.riskengine.engine.rules;

import com.ordenaris.riskengine.dto.EvaluationRequestDto;
import com.ordenaris.riskengine.dto.RuleEvaluationDto;
import com.ordenaris.riskengine.engine.RuleContext;
import com.ordenaris.riskengine.enums.ProductoFinanciero;

public class ProductoEstrictoRule implements Rule {

    @Override
    public RuleEvaluationDto evaluate(EvaluationRequestDto request, RuleContext context) {
        RuleEvaluationDto result = new RuleEvaluationDto();
        result.setNombreRegla(getName());
        if (context.productoFinanciero == ProductoFinanciero.ARRENDAMIENTO_FINANCIERO) {
            result.setResultado("ALTO");
        } else {
            result.setResultado("OK");
        }
        return result;
    }

    @Override
    public String getName() {
        return "Producto Estricto";
    }
}