package br.com.softexpert.acoes.email;

import br.com.softexpert.acoes.objects.Conta;
import br.com.softexpert.acoes.objects.DadosNegociacao;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class GeradorExcel {
    public static void gerar(ArrayList<DadosNegociacao> relatorio,List<Conta> contas) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Relatório");

        sheet.setDefaultColumnWidth(25);
        sheet.setDefaultRowHeight((short)400);

        int rownum = 0;
        int cellnum = 0;
        Cell cell;
        Row row;

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        headerStyle.setAlignment(CellStyle.ALIGN_CENTER);
        headerStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        CellStyle textStyle = workbook.createCellStyle();
        textStyle.setAlignment(CellStyle.ALIGN_CENTER);
        textStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        CellStyle numberStyle = workbook.createCellStyle();
        numberStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        row = sheet.createRow(rownum++);
        cell = row.createCell(cellnum++);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("Data");

        cell = row.createCell(cellnum++);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("Empresa");

        cell = row.createCell(cellnum++);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("Quantidade");

        cell = row.createCell(cellnum++);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("Tipo");

        cell = row.createCell(cellnum++);
        cell.setCellStyle(headerStyle);
        cell.setCellValue("Valor");

        for (DadosNegociacao transacao : relatorio) {

            DecimalFormat valor = new DecimalFormat("#,###,##0.00");


            row = sheet.createRow(rownum++);
            cellnum = 0;

            cell = row.createCell(cellnum++);
            cell.setCellStyle(textStyle);
            cell.setCellValue(transacao.getData());

            cell = row.createCell(cellnum++);
            cell.setCellStyle(textStyle);
            cell.setCellValue(transacao.getEmpresa());

            cell = row.createCell(cellnum++);
            cell.setCellStyle(numberStyle);
            cell.setCellValue(valor.format(transacao.getQuantidade()));

            cell = row.createCell(cellnum++);
            cell.setCellStyle(textStyle);
            cell.setCellValue(transacao.getTipo());

            cell = row.createCell(cellnum++);
            cell.setCellStyle(textStyle);
            cell.setCellValue("R$"+valor.format(transacao.getValor()));
        }

        try {
            DadosNegociacao firstOb = relatorio.get(0);
            DadosNegociacao lastOb = relatorio.get(relatorio.size() - 1);

            String first = String.valueOf(firstOb.getId());
            String last = String.valueOf(lastOb.getId());

            String path = "C:\\workspace\\relatorio"+first+"-"+last+".xls";
            File xls = new File(path);
            xls.createNewFile();
            FileOutputStream out = new FileOutputStream(xls,false);
            workbook.write(out);
            out.close();
            workbook.close();

            EnviaEmail envia = new EnviaEmail();
            envia.envia(contas,path);
            xls.delete();
            System.out.println("Sucesso!");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
