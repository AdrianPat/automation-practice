package utilities;

import org.testng.annotations.DataProvider;

import java.io.IOException;

import static utilities.ReadingDataFromExcel.getDataFromExcel;

public abstract class DataProviders {

    @DataProvider
    public static Object[][] dataSetForInvalidSignIn() throws IOException {
        String filePath = ".\\testData\\dataSetForInvalidSignIn.xlsx";
        return getDataFromExcel(filePath);
    }
}
