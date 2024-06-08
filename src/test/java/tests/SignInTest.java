package tests;

import config.TestConfig;
import org.testng.annotations.Test;
import pages.Home;
import utilities.DataProviders;

public class SignInTest extends TestConfig {

    @Test
    public void successfulSignIn() {
        new Home()
                .openSignInPage()
                .submitSignIn()
                .userShouldBeSuccessfullySignedIn();
    }

    @Test(dataProvider = "dataSetForInvalidSignIn", dataProviderClass = DataProviders.class)
    public void unsuccessfulSignInWithInvalidData(String email, String password) {
        new Home()
                .openSignInPage()
                .submitSignInWithInvalidData(email, password)
                .submitSignInWithInvalidDataShouldFail();
    }
}
