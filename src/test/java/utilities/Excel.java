package utilities;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public abstract class Excel {

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
        workbook.close();
        inputStream.close();
        return data;
    }

    public static void saveNewUserIntoExcel(Object[] userInfo) {
        try {
            String filePath = ".\\testData\\myUsers.xlsx";
            FileInputStream inputStream = new FileInputStream(filePath);
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);
            XSSFRow newRow = sheet.createRow(sheet.getLastRowNum() + 1);

            int cellNum = 0;

            for (Object value : userInfo) {
                XSSFCell cell = newRow.createCell(cellNum++);
                if (value instanceof String) {
                    cell.setCellValue((String) value);
                } else if (value instanceof Number) {
                    cell.setCellValue((double) value);
                } else if (value instanceof Boolean) {
                    cell.setCellValue((boolean) value);
                } else {
                    cell.setCellValue(String.valueOf(value));
                }
            }
            FileOutputStream outputStream = new FileOutputStream(filePath);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[] getRandomUserFromExcel() {
        int numberOfColumns = 4;
        String[] userInfo = new String[numberOfColumns];
        try {
            String filePath = ".\\testData\\myUsers.xlsx";
            FileInputStream inputStream = new FileInputStream(filePath);
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);

            int rows = sheet.getLastRowNum();
            int userNum = new Random().nextInt(rows);

            XSSFRow row = sheet.getRow(userNum + 1);

            for (int i = 0; i < numberOfColumns; i++) {
                userInfo[i] = row.getCell(i).getStringCellValue();
            }
            workbook.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userInfo;
    }
}
