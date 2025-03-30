package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelExporter {
    
    private static final String FILE_PATH = "StudyTimeLog.xlsx";

    public static void exportSeconds(int totalSeconds) {
        try {
            File file = new File(FILE_PATH);

            XSSFWorkbook workbook;
            XSSFSheet sheet;

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat monthFormat = new SimpleDateFormat("MMMMyyyy");

            String currentDate = dateFormat.format(new Date());
            String currentMonth = monthFormat.format(new Date());

            if(file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                workbook = new XSSFWorkbook(fis);
                sheet = workbook.getSheet(currentMonth);
                if (sheet == null) {
                    sheet = workbook.createSheet(currentMonth);
                    createHeaderRow(sheet);
                }
                fis.close();
            } else {
                workbook = new XSSFWorkbook();
                sheet = workbook.createSheet(currentMonth);
                createHeaderRow(sheet);
            }

            int rowNumber = sheet.getLastRowNum() + 1;
            Row row = sheet.createRow(rowNumber);

            Cell dateCell = row.createCell(0);
            dateCell.setCellValue(currentDate);

            Cell timeCell = row.createCell(1);
            double excelTimeValue = totalSeconds / 86400.0;
            timeCell.setCellValue(excelTimeValue);

            Cell sumCell = row.createCell(2);
            String sumFormula = "SUM(B2:B" + (rowNumber + 1) + ")";
            sumCell.setCellFormula(sumFormula);


            CellStyle timeStyle = sheet.getWorkbook().createCellStyle();
            timeStyle.setDataFormat(sheet.getWorkbook().createDataFormat().getFormat("hh:mm:ss"));
            timeCell.setCellStyle(timeStyle);

            CellStyle sumStyle = sheet.getWorkbook().createCellStyle();
            sumStyle.setDataFormat(sheet.getWorkbook().createDataFormat().getFormat("[d]:hh:mm:ss"));
            sumCell.setCellStyle(sumStyle);

            FileOutputStream fos = new FileOutputStream(FILE_PATH);
                workbook.write(fos);
                fos.close();
                workbook.close();

            System.out.println("Study session logged successfully!");  
        }

        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private static void createHeaderRow(XSSFSheet sheet) {
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Study Date");
        headerRow.createCell(1).setCellValue("Time Spent (hh:mm:ss)");
        headerRow.createCell(2).setCellValue("Total Time Sum (days:hh:mm:ss)");
    }

    private static String formatTimeForExcel(int totalSeconds) {
        int hours = totalSeconds / 3600;
        int minutes = (totalSeconds % 3600) / 60;
        int secs = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, secs);
    }
}
