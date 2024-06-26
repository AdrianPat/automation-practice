package utilities;

import org.testng.annotations.DataProvider;

import java.io.IOException;

import static utilities.Excel.getDataFromExcel;

public abstract class DataProviders {

    @DataProvider
    public static Object[][] dataSetForInvalidSignIn() throws IOException {
        String filePath = ".\\testData\\dataSetForInvalidSignIn.xlsx";
        return getDataFromExcel(filePath);
    }

    @DataProvider
    public static Object[][] dataSetWithInvalidEmails() throws IOException {
        String filePath = ".\\testData\\dataSetWithInvalidEmails.xlsx";
        return getDataFromExcel(filePath);
    }

    @DataProvider
    public static Object[][] dataSetForInvalidRegistration() throws IOException {
        String filePath = ".\\testData\\dataSetForInvalidRegistration.xlsx";
        return getDataFromExcel(filePath);
    }

    @DataProvider
    public static Object[][] dataSetWithInvalidDates() throws IOException {
        String filePath = ".\\testData\\dataSetWithInvalidDates.xlsx";
        return getDataFromExcel(filePath);
    }

    @DataProvider
    public static Object[][] dataSetWithProductsToSearch() throws IOException {
        String filePath = ".\\testData\\dataSetWithProductsToSearch.xlsx";
        return getDataFromExcel(filePath);
    }

    @DataProvider
    public static Object[][] dataSetForInvalidSearch() throws IOException {
        String filePath = ".\\testData\\dataSetForInvalidSearch.xlsx";
        return getDataFromExcel(filePath);
    }
}
