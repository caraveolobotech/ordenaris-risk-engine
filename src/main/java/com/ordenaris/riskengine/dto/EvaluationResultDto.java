package com.ordenaris.riskengine.dto;

import java.util.List;
import lombok.Data;

@Data
public class EvaluationResultDto {
    public String nivelRiesgo; // BAJO, MEDIO, ALTO, RECHAZADO
    public List<RuleEvaluationDto> reglasEvaluadas;
    public String motivoFinal;
}