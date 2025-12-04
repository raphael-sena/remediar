package com.remediar.back_remediar.service;

import com.remediar.back_remediar.model.Medicamento;
import com.remediar.back_remediar.model.MedicamentoControlado;
import com.remediar.back_remediar.model.enums.CategoriaPortaria;
import com.remediar.back_remediar.model.enums.StatusProduto;
import com.remediar.back_remediar.repository.MedicamentoRepository;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class MedicamentoExcelService {

    private final MedicamentoRepository medicamentoRepository;

    public MedicamentoExcelService(MedicamentoRepository medicamentoRepository) {
        this.medicamentoRepository = medicamentoRepository;
    }

    // Importa medicamentos à partir da planilha da CMED de preços
    public void importarMedicamentos(InputStream inputPrecos, InputStream inputControlados) {
        try {
            Workbook workbookPrecos = getWorkbook(inputPrecos, "medicamentos_cmed.xls");
            Sheet sheetPrecos = workbookPrecos.getSheetAt(0);

            Workbook workbookControlados = getWorkbook(inputControlados, "SUBSTANCIAS PORTARIA 344-98.xlsx");
            Sheet sheetControlados = workbookControlados.getSheetAt(0);

            for (int i = 53; i <= sheetPrecos.getLastRowNum(); i++) {
                Row rowPrecos = sheetPrecos.getRow(i);

                if (rowPrecos == null ||
                        rowPrecos.getCell(0) == null ||
                        rowPrecos.getCell(0).getStringCellValue().isEmpty()) {
                    continue;
                }

                String principioAtivoPreco = rowPrecos.getCell(0).getStringCellValue();
                boolean encontradoComoControlado = false;

                for (int j = 1; j <= sheetControlados.getLastRowNum(); j++) {
                    Row rowControlados = sheetControlados.getRow(j);
                    if (rowControlados == null || rowControlados.getCell(0) == null) continue;

                    String principioAtivoControlado = rowControlados.getCell(0).getStringCellValue();
                    if (principioAtivoControlado.toLowerCase().contains(principioAtivoPreco.toLowerCase())) {
                        MedicamentoControlado medicamentoControlado = new MedicamentoControlado();
                        medicamentoControlado.setFromBase(true);
                        CellToAttribute(rowPrecos, medicamentoControlado);
                        medicamentoControlado.setCategoriaPortaria(CategoriaPortaria.fromDescricao(
                                rowControlados.getCell(1).getStringCellValue()
                        ));
                        medicamentoRepository.save(medicamentoControlado);
                        encontradoComoControlado = true;
                        break;
                    }
                }

                if (!encontradoComoControlado) {
                    Medicamento medicamento = new Medicamento();
                    medicamento.setFromBase(true);
                    CellToAttribute(rowPrecos, medicamento);
                    medicamentoRepository.save(medicamento);
                }
            }

            System.out.println("Primeiro elemento controlados: " + sheetControlados.getRow(1).getCell(0).getStringCellValue());
            System.out.println("Último elemento controlados: " + sheetControlados.getRow(sheetControlados.getLastRowNum()).getCell(0).getStringCellValue());
            System.out.println("Primeiro elemento preços: " + sheetPrecos.getRow(53).getCell(0).getStringCellValue());
            System.out.println("Último elemento preços: " + sheetPrecos.getRow(sheetPrecos.getLastRowNum()).getCell(0).getStringCellValue());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private Workbook getWorkbook(InputStream inputStream, String nomeArquivo) throws IOException {
        if (nomeArquivo.endsWith(".xlsx")) {
            return new XSSFWorkbook(inputStream);
        } else if (nomeArquivo.endsWith(".xls")) {
            return new HSSFWorkbook(inputStream);
        } else {
            throw new IllegalArgumentException("Formato de arquivo não suportado: " + nomeArquivo);
        }
    }


    public void CellToAttribute(Row rowPrecos, Medicamento medicamento) {
        medicamento.setNomeComercial(rowPrecos.getCell(8).getStringCellValue());
        medicamento.setPrincipioAtivo(rowPrecos.getCell(0).getStringCellValue());
        medicamento.setApresentacao(rowPrecos.getCell(9).getStringCellValue());
        medicamento.setCodigoBarras(rowPrecos.getCell(5).getStringCellValue());
        medicamento.setLaboratorio(rowPrecos.getCell(2).getStringCellValue());
        medicamento.setPrecoMaximo(Double.valueOf(rowPrecos.getCell(39).getStringCellValue()));

        String descricaoStatus = rowPrecos.getCell(11).getStringCellValue().trim();

        if (descricaoStatus.equals("-") || descricaoStatus.isBlank()) {
            medicamento.setStatusProduto(StatusProduto.SEM_STATUS);
        } else {
            medicamento.setStatusProduto(StatusProduto.fromDescricao(descricaoStatus));
        }
    }
}
