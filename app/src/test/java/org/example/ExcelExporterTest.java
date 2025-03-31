package org.example;

import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.*;

class ExcelExporterTest {

    @Test
    void testFileCreation() {
        File file = new File("StudyTimeLog.xlsx");
        if (file.exists()) {
            file.delete(); 
        }

        ExcelExporter.exportSeconds(3600);

        assertTrue(file.exists(), "Excel file should be created after exporting.");
    }

    @Test
    void testLogStudySession() {
        ExcelExporter.exportSeconds(3600);
        ExcelExporter.exportSeconds(1800);

        File file = new File("StudyTimeLog.xlsx");
        assertTrue(file.exists(), "Excel file should exist.");
    }
}