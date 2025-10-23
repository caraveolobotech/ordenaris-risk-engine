package com.ordenaris.riskengine.service;

import org.springframework.stereotype.Service;

import com.ordenaris.riskengine.dto.EvaluationRequestDto;
import com.ordenaris.riskengine.dto.EvaluationResultDto;
import com.ordenaris.riskengine.engine.OrdenarisRiskEngine;
import com.ordenaris.riskengine.engine.RuleContext;
import com.ordenaris.riskengine.providers.DatosContablesProvider;
import com.ordenaris.riskengine.providers.HistorialPagosProvider;
import com.ordenaris.riskengine.providers.VerificacionLegalProvider;

@Service
public class EvaluationService {

    private final OrdenarisRiskEngine engine;
    private final DatosContablesProvider datosContablesProvider;
    private final HistorialPagosProvider historialPagosProvider;
    private final VerificacionLegalProvider verificacionLegalProvider;

    public EvaluationService(OrdenarisRiskEngine engine,
                             DatosContablesProvider datosContablesProvider,
                             HistorialPagosProvider historialPagosProvider,
                             VerificacionLegalProvider verificacionLegalProvider) {
        this.engine = engine;
        this.datosContablesProvider = datosContablesProvider;
        this.historialPagosProvider = historialPagosProvider;
        this.verificacionLegalProvider = verificacionLegalProvider;
    }

    public EvaluationResultDto evaluar(EvaluationRequestDto request) {
        RuleContext context = new RuleContext();
        context.promedioVentas = datosContablesProvider.getPromedioVentas(request.getEmpresaId());
        context.deudaVencidaMayor90Dias = historialPagosProvider.tieneDeudaVencida(request.getEmpresaId());
        context.juicioMercantilAbierto = verificacionLegalProvider.tieneJuicio(request.getEmpresaId());
        context.historialExcelente = historialPagosProvider.historialExcelente(request.getEmpresaId());

        return engine.evaluate(request, context);
    }
}