package com.ordenaris.riskengine.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ordenaris.riskengine.engine.OrdenarisRiskEngine;
import com.ordenaris.riskengine.engine.rules.AltaSolicitudVsVentasRule;
import com.ordenaris.riskengine.engine.rules.DemandaLegalAbiertaRule;
import com.ordenaris.riskengine.engine.rules.DeudaActivaRule;
import com.ordenaris.riskengine.engine.rules.EmpresaNuevaRule;
import com.ordenaris.riskengine.engine.rules.HistorialExcelenteRule;
import com.ordenaris.riskengine.engine.rules.ProductoEstrictoRule;
import com.ordenaris.riskengine.engine.rules.Rule;

@Configuration
public class EngineConfig {

    @Bean
    public OrdenarisRiskEngine ordenarisRiskEngine(List<Rule> rules) {
        return new OrdenarisRiskEngine(rules);
    }

    // Define tus reglas como beans también
    @Bean public Rule deudaActivaRule() { return new DeudaActivaRule(); }
    @Bean public Rule altaSolicitudVsVentasRule() { return new AltaSolicitudVsVentasRule(); }
    @Bean public Rule empresaNuevaRule() { return new EmpresaNuevaRule(); }
    @Bean public Rule demandaLegalAbiertaRule() { return new DemandaLegalAbiertaRule(); }
    @Bean public Rule historialExcelenteRule() { return new HistorialExcelenteRule(); }
    @Bean public Rule productoEstrictoRule() { return new ProductoEstrictoRule(); }
}