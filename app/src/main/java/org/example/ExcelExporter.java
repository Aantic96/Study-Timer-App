package org.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelExporter {

    public static void exportSeconds(int totalSeconds, String FILE_PATH) {
        try {
            File file = new File(FILE_PATH);
            XSSFWorkbook workbook;
            XSSFSheet sheet;

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat monthFormat = new SimpleDateFormat("MMMMyyyy");

            String currentDate = dateFormat.format(new Date());
            String currentMonth = monthFormat.format(new Date());
            String totalTimeString = "";

            if (file.exists()) {
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

            row.createCell(2);

            CellStyle timeStyle = sheet.getWorkbook().createCellStyle();
            timeStyle.setDataFormat(sheet.getWorkbook().createDataFormat().getFormat("hh:mm:ss"));
            timeCell.setCellStyle(timeStyle);

            row.getCell(2).setCellStyle(timeStyle);

            if (rowNumber >= 1) {
                double totalTime = 0.0;
                for (int i = 1; i <= rowNumber; i++) {
                    Row r = sheet.getRow(i);
                    totalTime += r.getCell(1).getNumericCellValue();
                }

                Row totalRow = sheet.getRow(1); 
                if (totalRow == null) {
                    totalRow = sheet.createRow(1);
                }
                Cell totalTimeCell = totalRow.createCell(3);                

                totalTimeString = convertTotalTimeToStr(totalTime * 86400.0);
                totalTimeCell.setCellValue(totalTimeString);
            }

            FileOutputStream fos = new FileOutputStream(FILE_PATH);
            workbook.write(fos);
            fos.close();
            workbook.close();

            System.out.println("Study session logged successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createHeaderRow(XSSFSheet sheet) {
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Study Date");
        headerRow.createCell(1).setCellValue("Time Spent (hh:mm:ss)");
        headerRow.createCell(2);
        headerRow.createCell(3).setCellValue("Total Time");
    }

    private static String convertTotalTimeToStr(double totalTime) {
        long secondsTotal = Math.round(totalTime);

        int day = (int)TimeUnit.SECONDS.toDays(secondsTotal);        
        long hours = TimeUnit.SECONDS.toHours(secondsTotal) - (day *24);
        long minute = TimeUnit.SECONDS.toMinutes(secondsTotal) - (TimeUnit.SECONDS.toHours(secondsTotal)* 60);
        long second = TimeUnit.SECONDS.toSeconds(secondsTotal) - (TimeUnit.SECONDS.toMinutes(secondsTotal) *60);

        return "Days: " + day + " Hours: " + hours + " Minutes: " + minute + " Seconds: " + second;
    }
}
