package utilities;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

public abstract class ReadingDataFromExcel {

    public static Object[][] getDataFromExcel(String filePath) throws IOException {
        FileInputStream inputStream = new FileInputStream(filePath);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);

        int rows = sheet.getLastRowNum();
        int cols = sheet.getRow(0).getLastCellNum();

        Object[][] data = new Object[rows][cols];

        for (int i = 0; i < rows; i++) {
            XSSFRow row = sheet.getRow(i + 1);
            for (int j = 0; j < cols; j++) {
                XSSFCell cell = row.getCell(j);
                CellType cellType = cell.getCellType();
                switch (cellType) {
                    case STRING:
                        data[i][j] = cell.getStringCellValue();
                        break;
                    case NUMERIC:
                        data[i][j] = cell.getNumericCellValue();
                        break;
                    case BOOLEAN:
                        data[i][j] = cell.getBooleanCellValue();
                        break;
                    default:
                        data[i][j] = "";
                }
            }
        }
        return data;
    }
}
