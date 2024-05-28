package tests;

import config.TestConfig;
import org.testng.annotations.Test;
import pages.Home;

public class SignUpTest extends TestConfig {

    @Test
    public void successfulRegister() {
        new Home()
                .openSignInPage()
                .submitCreateAccountFormWithValidEmail()
                .submitFormWithValidData()
                .userShouldBeSuccessfullyRegistered();
    }

    @Test
    public void registrationWithInvalidDataShouldFail() {
        new Home()
                .openSignInPage()
                .submitCreateAccountFormWithValidEmail()
                .submitFormWithInvalidData()
                .userShouldSeeRegistrationFormAlert();
    }
}
