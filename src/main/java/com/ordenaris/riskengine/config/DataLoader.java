package com.ordenaris.riskengine.config;

import com.ordenaris.riskengine.providers.entity.DatosContablesEntity;
import com.ordenaris.riskengine.providers.entity.HistorialPagosEntity;
import com.ordenaris.riskengine.providers.entity.VerificacionLegalEntity;
import com.ordenaris.riskengine.providers.repository.DatosContablesRepository;
import com.ordenaris.riskengine.providers.repository.HistorialPagosRepository;
import com.ordenaris.riskengine.providers.repository.VerificacionLegalRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner loadData(DatosContablesRepository datosRepo,
                                      HistorialPagosRepository pagosRepo,
                                      VerificacionLegalRepository legalRepo) {
        return args -> {
            DatosContablesEntity d1 = new DatosContablesEntity();
            d1.setEmpresaId("empresaA");
            d1.setVentasPromedio(BigDecimal.valueOf(20000));
            d1.setActivos(BigDecimal.valueOf(50000));
            d1.setPasivos(BigDecimal.valueOf(10000));
            datosRepo.save(d1);

            HistorialPagosEntity h1 = new HistorialPagosEntity();
            h1.setEmpresaId("empresaA");
            h1.setDeudaVencida(false);
            h1.setHistorialExcelente(true);
            pagosRepo.save(h1);

            VerificacionLegalEntity v1 = new VerificacionLegalEntity();
            v1.setEmpresaId("empresaA");
            v1.setJuicioMercantilAbierto(false);
            legalRepo.save(v1);
        };
    }
}