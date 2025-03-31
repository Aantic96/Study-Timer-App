package org.example;

import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExcelExporterTest {

    @Test
    void testExportSeconds() {
        String testFilePath = "TestStudyLog.xlsx";

        ExcelExporter.exportSeconds(3661, testFilePath);

        File file = new File(testFilePath);
        assertTrue(file.exists());

        file.delete();
    }
}
