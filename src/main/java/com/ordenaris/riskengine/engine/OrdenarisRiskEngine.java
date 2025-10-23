package com.ordenaris.riskengine.engine;

import java.util.ArrayList;
import java.util.List;

import com.ordenaris.riskengine.dto.EvaluationRequestDto;
import com.ordenaris.riskengine.dto.EvaluationResultDto;
import com.ordenaris.riskengine.dto.RuleEvaluationDto;
import com.ordenaris.riskengine.engine.rules.Rule;

public class OrdenarisRiskEngine {

    private final List<Rule> rules;

    public OrdenarisRiskEngine(List<Rule> rules) {
        this.rules = rules;
    }

    public EvaluationResultDto evaluate(EvaluationRequestDto request, RuleContext context) {
        List<RuleEvaluationDto> evaluaciones = new ArrayList<>();
        String nivelRiesgo = "BAJO";
        String motivoFinal = "";

        for (Rule rule : rules) {
            RuleEvaluationDto resultado = rule.evaluate(request, context);
            evaluaciones.add(resultado);

            switch (resultado.getResultado()) {
                case "RECHAZADO":
                    nivelRiesgo = "RECHAZADO";
                    motivoFinal = resultado.getNombreRegla();
                    break;
                case "ALTO":
                    if (!nivelRiesgo.equals("RECHAZADO")) {
                        nivelRiesgo = "ALTO";
                        motivoFinal = resultado.getNombreRegla();
                    }
                    break;
                case "MEDIO":
                    if (nivelRiesgo.equals("BAJO")) {
                        nivelRiesgo = "MEDIO";
                        motivoFinal = resultado.getNombreRegla();
                    }
                    break;
                case "MEJORA":
                    if (nivelRiesgo.equals("ALTO")) nivelRiesgo = "MEDIO";
                    else if (nivelRiesgo.equals("MEDIO")) nivelRiesgo = "BAJO";
                    break;
            }
        }

        EvaluationResultDto resultDto = new EvaluationResultDto();
        resultDto.setNivelRiesgo(nivelRiesgo);
        resultDto.setReglasEvaluadas(evaluaciones);
        resultDto.setMotivoFinal(motivoFinal);

        return resultDto;
    }
}