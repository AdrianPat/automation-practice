package tests;

import config.TestConfig;
import enums.Email;
import org.testng.annotations.Test;
import pages.Home;
import utilities.DataProviders;

public class SignUpTest extends TestConfig {

    @Test
    public void successfulRegister() {
        new Home()
                .openSignInPage()
                .submitCreateAccountForm()
                .submitRegistrationForm()
                .userShouldBeSuccessfullyRegistered();
    }

    @Test
    public void submitEmptyRegistrationFormShouldFail() {
        new Home()
                .openSignInPage()
                .submitCreateAccountForm()
                .submitEmptyRegistrationForm()
                .userShouldSeeAllAlertMessages();
    }

    @Test(dataProvider = "dataSetWithInvalidEmails", dataProviderClass = DataProviders.class)
    public void submitCreateAccountFormWithInvalidEmailShouldFail(String invalidEmail) {
        new Home()
                .openSignInPage()
                .submitCreateAccountFormWithInvalidEmail(invalidEmail)
                .userShouldSeeCreateAccountAlertMessage(Email.INVALID);
    }

    @Test
    public void submitCreateAccountFormWithTakenEmailShouldFail() {
        new Home()
                .openSignInPage()
                .submitCreateAccountFormWithTakenEmail()
                .userShouldSeeCreateAccountAlertMessage(Email.TAKEN);
    }

    @Test(dataProvider = "dataSetForInvalidRegistration", dataProviderClass = DataProviders.class)
    public void registrationWithInvalidDataShouldFail(String name, String lastName, String email, String password) {
        new Home()
                .openSignInPage()
                .submitCreateAccountForm()
                .submitRegistrationFormWithInvalidData(name, lastName, email, password)
                .userShouldSeeRegistrationFormAlert();
    }
}
