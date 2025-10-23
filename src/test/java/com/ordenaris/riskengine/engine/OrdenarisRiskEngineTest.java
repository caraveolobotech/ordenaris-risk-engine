package com.ordenaris.riskengine.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.ordenaris.riskengine.dto.EvaluationRequestDto;
import com.ordenaris.riskengine.dto.EvaluationResultDto;
import com.ordenaris.riskengine.dto.RuleEvaluationDto;
import com.ordenaris.riskengine.engine.rules.Rule;
import com.ordenaris.riskengine.enums.ProductoFinanciero;

class OrdenarisRiskEngineTest {

    // Helper: crea un Rule stub que devuelve un RuleEvaluationDto con nombre y resultado dado
    private static Rule stubRule(String nombre, String resultado) {
        return new Rule() {
            @Override
            public RuleEvaluationDto evaluate(EvaluationRequestDto request, RuleContext context) {
                RuleEvaluationDto r = new RuleEvaluationDto();
                r.setNombreRegla(nombre);
                r.setResultado(resultado);
                return r;
            }

            @Override
            public String getName() {
                return nombre;
            }
        };
    }

    private EvaluationRequestDto sampleRequest() {
        EvaluationRequestDto r = new EvaluationRequestDto();
        r.setEmpresaId("EMP-1");
        r.setMontoSolicitado(BigDecimal.valueOf(1000));
        r.setProductoFinanciero(ProductoFinanciero.LINEA_OPERATIVA);
        r.setFechaSolicitud(LocalDate.now());
        return r;
    }

    private RuleContext sampleContext() {
        RuleContext c = new RuleContext();
        c.promedioVentas = BigDecimal.valueOf(500);
        c.juicioMercantilAbierto = false;
        c.deudaVencidaMayor90Dias = false;
        c.historialExcelente = false;
        c.montoSolicitado = BigDecimal.valueOf(1000);
        return c;
    }

    @Test
    void whenAnyRuleReturnsRejected_thenOverallIsRejectedAndMotivoIsThatRule() {
        List<Rule> rules = Arrays.asList(
                stubRule("A", "OK"),
                stubRule("RechazoRule", "RECHAZADO"),
                stubRule("B", "ALTO")
        );

        OrdenarisRiskEngine engine = new OrdenarisRiskEngine(rules);
        EvaluationResultDto res = engine.evaluate(sampleRequest(), sampleContext());

        assertEquals("RECHAZADO", res.getNivelRiesgo());
        assertEquals("RechazoRule", res.getMotivoFinal());
        assertEquals(3, res.getReglasEvaluadas().size());
    }

    @Test
    void whenAtLeastOneHighAndNoRejected_thenOverallIsAlto() {
        List<Rule> rules = Arrays.asList(
                stubRule("A", "OK"),
                stubRule("Alta", "ALTO"),
                stubRule("C", "OK")
        );

        OrdenarisRiskEngine engine = new OrdenarisRiskEngine(rules);
        EvaluationResultDto res = engine.evaluate(sampleRequest(), sampleContext());

        assertEquals("ALTO", res.getNivelRiesgo());
        // motivoFinal debe ser la regla ALTO que lo provocó (según tu engine toma la que setea)
        assertEquals("Alta", res.getMotivoFinal());
    }

    @Test
    void mejoraReducesRiskLevel() {
        // Empezamos con ALTO por la segunda regla, luego MEJORA baja un nivel
        List<Rule> rules = Arrays.asList(
                stubRule("Alta", "ALTO"),
                stubRule("Historial", "MEJORA")
        );

        OrdenarisRiskEngine engine = new OrdenarisRiskEngine(rules);
        EvaluationResultDto res = engine.evaluate(sampleRequest(), sampleContext());

        // según la lógica que compartiste: ALTO + MEJORA => MEDIO
        assertEquals("MEDIO", res.getNivelRiesgo());
        // motivoFinal se queda con la regla que produjo ALTO (Alta)
        assertEquals("Alta", res.getMotivoFinal());
    }

    @Test
    void mediumWhenOnlyMediumRules_andNoHigher() {
        List<Rule> rules = Arrays.asList(
                stubRule("Medio1", "MEDIO"),
                stubRule("Ok", "OK")
        );

        OrdenarisRiskEngine engine = new OrdenarisRiskEngine(rules);
        EvaluationResultDto res = engine.evaluate(sampleRequest(), sampleContext());

        assertEquals("MEDIO", res.getNivelRiesgo());
        assertEquals("Medio1", res.getMotivoFinal());
    }

    @Test
    void lowWhenAllOk() {
        List<Rule> rules = Arrays.asList(
                stubRule("A", "OK"),
                stubRule("B", "OK")
        );

        OrdenarisRiskEngine engine = new OrdenarisRiskEngine(rules);
        EvaluationResultDto res = engine.evaluate(sampleRequest(), sampleContext());

        assertEquals("BAJO", res.getNivelRiesgo());

        // motivoFinal puede estar vacío si el engine no asignó ninguno cuando todo es OK.
        // Aseguramos que no sea nulo y, si no está vacío, entonces verificar que contiene texto.
        assertNotNull(res.getMotivoFinal(), "motivoFinal no debe ser null");
        if (!res.getMotivoFinal().isEmpty()) {
            // si se asignó, validar que no sea solo espacios
            assertFalse(res.getMotivoFinal().trim().isEmpty(), "motivoFinal no debe ser solo espacios");
        }
    }
}