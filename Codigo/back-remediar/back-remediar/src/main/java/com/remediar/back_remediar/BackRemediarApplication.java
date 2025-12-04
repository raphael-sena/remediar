package com.remediar.back_remediar;

import com.remediar.back_remediar.service.MedicamentoExcelService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;

@SpringBootApplication
public class BackRemediarApplication implements CommandLineRunner {

	@Value("${app.loadInitialData}")
	private boolean loadInitialData;

	private final MedicamentoExcelService medicamentoExcelService;

    public BackRemediarApplication(MedicamentoExcelService medicamentoExcelService) {
        this.medicamentoExcelService = medicamentoExcelService;
    }

    public static void main(String[] args) {
		SpringApplication.run(BackRemediarApplication.class, args);
	}

	@Override
	public void run(String... args) {
		if (loadInitialData) {
			try (
					InputStream inputPreco = getClass().getClassLoader().getResourceAsStream("medicamentos_cmed.xls");
					InputStream inputControlados = getClass().getClassLoader().getResourceAsStream("SUBSTANCIAS PORTARIA 344-98.xlsx")
			) {
				if (inputPreco == null || inputControlados == null) {
					System.err.println("❌ Arquivos não encontrados no classpath!");
					return;
				}

				Instant start = Instant.now();
				medicamentoExcelService.importarMedicamentos(inputPreco, inputControlados);
				Instant end = Instant.now();
				System.out.println("✅ Tempo de execução: " + ((end.toEpochMilli() - start.toEpochMilli()) / 1000) + " s");

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


}
