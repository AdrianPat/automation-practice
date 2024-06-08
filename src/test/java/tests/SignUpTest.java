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
    public void unsuccessfulSubmitEmptyRegistrationForm() {
        new Home()
                .openSignInPage()
                .submitCreateAccountForm()
                .submitEmptyRegistrationForm()
                .userShouldSeeAllAlertMessages();
    }

    @Test(dataProvider = "dataSetWithInvalidEmails", dataProviderClass = DataProviders.class)
    public void unsuccessfulSubmitCreateAccountFormWithInvalidEmail(String invalidEmail) {
        new Home()
                .openSignInPage()
                .submitCreateAccountFormWithInvalidEmail(invalidEmail)
                .userShouldSeeCreateAccountAlertMessage(Email.INVALID);
    }

    @Test
    public void unsuccessfulSubmitCreateAccountFormWithTakenEmail() {
        new Home()
                .openSignInPage()
                .submitCreateAccountFormWithTakenEmail()
                .userShouldSeeCreateAccountAlertMessage(Email.TAKEN);
    }

    @Test(dataProvider = "dataSetForInvalidRegistration", dataProviderClass = DataProviders.class)
    public void unsuccessfulRegistrationWithInvalidData(String name, String lastName, String email, String password) {
        new Home()
                .openSignInPage()
                .submitCreateAccountForm()
                .submitRegistrationFormWithInvalidData(name, lastName, email, password)
                .userShouldSeeRegistrationFormAlert();
    }

    @Test(dataProvider = "dataSetWithInvalidDates", dataProviderClass = DataProviders.class)
    public void unsuccessfulRegistrationWithInvalidDateOfBirth(String day, String month, String year) {
        new Home()
                .openSignInPage()
                .submitCreateAccountForm()
                .submitRegistrationFormWithInvalidDate(day, month, year)
                .userShouldSeeInvalidDateAlert();
    }
}
