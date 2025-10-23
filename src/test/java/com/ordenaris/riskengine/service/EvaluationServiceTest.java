package com.ordenaris.riskengine.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import com.ordenaris.riskengine.dto.EvaluationRequestDto;
import com.ordenaris.riskengine.dto.EvaluationResultDto;
import com.ordenaris.riskengine.engine.OrdenarisRiskEngine;
import com.ordenaris.riskengine.engine.RuleContext;
import com.ordenaris.riskengine.enums.ProductoFinanciero;
import com.ordenaris.riskengine.providers.DatosContablesProvider;
import com.ordenaris.riskengine.providers.HistorialPagosProvider;
import com.ordenaris.riskengine.providers.VerificacionLegalProvider;

class EvaluationServiceTest {

    private DatosContablesProvider datosContablesProvider;
    private HistorialPagosProvider historialPagosProvider;
    private VerificacionLegalProvider verificacionLegalProvider;
    private OrdenarisRiskEngine engine;
    private EvaluationService service;

    @BeforeEach
    void setup() {
        datosContablesProvider = mock(DatosContablesProvider.class);
        historialPagosProvider = mock(HistorialPagosProvider.class);
        verificacionLegalProvider = mock(VerificacionLegalProvider.class);
        engine = mock(OrdenarisRiskEngine.class);

        service = new EvaluationService(engine, datosContablesProvider, historialPagosProvider, verificacionLegalProvider);
    }

    @Test
    void serviceBuildsContextAndCallsEngine() {
        EvaluationRequestDto request = new EvaluationRequestDto();
        request.setEmpresaId("X-1");
        request.setFechaSolicitud(LocalDate.of(2025, 1, 1));
        request.setMontoSolicitado(BigDecimal.valueOf(10_000));
        request.setProductoFinanciero(ProductoFinanciero.CREDITO_REVOLVENTE);

        when(datosContablesProvider.getPromedioVentas("X-1")).thenReturn(BigDecimal.valueOf(2000));
        when(historialPagosProvider.tieneDeudaVencida("X-1")).thenReturn(false);
        when(historialPagosProvider.historialExcelente("X-1")).thenReturn(true);
        when(verificacionLegalProvider.tieneJuicio("X-1")).thenReturn(false);

        EvaluationResultDto fakeResult = new EvaluationResultDto();
        fakeResult.setNivelRiesgo("MEDIO");
        when(engine.evaluate(any(EvaluationRequestDto.class), any(RuleContext.class))).thenReturn(fakeResult);

        EvaluationResultDto result = service.evaluar(request);

        assertNotNull(result);
        assertEquals("MEDIO", result.getNivelRiesgo());

        // Verificamos que providers fueron llamados con el mismo empresaId
        verify(datosContablesProvider).getPromedioVentas("X-1");
        verify(historialPagosProvider).tieneDeudaVencida("X-1");
        verify(historialPagosProvider).historialExcelente("X-1");
        verify(verificacionLegalProvider).tieneJuicio("X-1");

        // Capturamos el RuleContext con el que se llamó al engine para validar valores
        ArgumentCaptor<RuleContext> ctxCaptor = ArgumentCaptor.forClass(RuleContext.class);
        verify(engine).evaluate(eq(request), ctxCaptor.capture());
        RuleContext ctx = ctxCaptor.getValue();

        assertEquals(BigDecimal.valueOf(2000), ctx.promedioVentas);
        assertEquals(false, ctx.deudaVencidaMayor90Dias);
        assertEquals(true, ctx.historialExcelente);
        assertEquals(false, ctx.juicioMercantilAbierto);
    }
}