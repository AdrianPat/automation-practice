package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import utilities.DataFaker;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static utilities.AlertMessageContent.getAlertContent;
import static utilities.Screenshots.captureFullPageScreenshot;

public class SignUp extends BasePage {

    public SignUp() {
        super();
    }

    private final DataFaker faker = new DataFaker();

    @FindBy(id = "customer_firstname")
    private WebElement customerFirstNameInput;

    @FindBy(id = "customer_lastname")
    private WebElement customerLastNameInput;

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "passwd")
    private WebElement passwordInput;

    @FindBy(id = "days")
    private WebElement birthdayDaySelect;

    @FindBy(id = "months")
    private WebElement birthdayMonthSelect;

    @FindBy(id = "years")
    private WebElement birthdayYearSelect;

    @FindBy(id = "uniform-newsletter")
    private WebElement newsletterCheckbox;

    @FindBy(id = "submitAccount")
    private WebElement registrationButton;

    @FindBy(css = "#center_column > .alert li")
    private List<WebElement> errorAlerts;

    @FindBy(css = "[type='radio']")
    private List<WebElement> gendersRadioButtons;

    /*  REGISTRATION — HAPPY PATH  */

    private void fillInRegistrationForm() {
        if (new Random().nextBoolean()) {
            gendersRadioButtons.get(new Random().nextInt(gendersRadioButtons.size())).click();
        }
        customerFirstNameInput.sendKeys(faker.getFakeFirstName());
        customerLastNameInput.sendKeys(faker.getFakeLastName());
        passwordInput.sendKeys(faker.getFakePassword());
        if (new Random().nextBoolean()) {
            Date fakeDate = faker.getFakeDateOfBirthday();
            new Select(birthdayDaySelect).selectByValue(String.valueOf(fakeDate.getDate()));
            new Select(birthdayMonthSelect).selectByValue(String.valueOf(fakeDate.getMonth() + 1));
            new Select(birthdayYearSelect).selectByValue(String.valueOf(fakeDate.getYear() + 1900));
        }
        if (new Random().nextBoolean()) newsletterCheckbox.click();
    }

    @Step
    public Profile submitRegistrationForm() {
        fillInRegistrationForm();
        saveNewCustomerToFile();
        captureFullPageScreenshot();
        registrationButton.click();
        return new Profile();
    }

    /*  REGISTRATION — NEGATIVE PATH  */

    @Step
    public SignUp submitEmptyRegistrationForm() {
        registrationButton.click();
        return this;
    }

    @Step
    public void userShouldSeeAllAlertMessages() {
        List<String> errors = getAlertContent(errorAlerts);
        List<String> expectedErrors = new ArrayList<>();
        expectedErrors.add("lastname is required.");
        expectedErrors.add("firstname is required.");
        expectedErrors.add("passwd is required.");
        Assert.assertEquals(errors, expectedErrors);
    }

    private void fillInRegistrationFormWithInvalidData(String name, String lastName, String email, String password) {
        customerFirstNameInput.sendKeys(name);
        customerLastNameInput.sendKeys(lastName);
        emailInput.clear();
        emailInput.sendKeys(email);
        passwordInput.sendKeys(password);
    }

    @Step
    public SignUp submitRegistrationFormWithInvalidData(String name, String lastName, String email, String password) {
        fillInRegistrationFormWithInvalidData(name, lastName, email, password);
        captureFullPageScreenshot();
        registrationButton.click();
        return this;
    }

    @Step
    public void userShouldSeeRegistrationFormAlert() {
        Assert.assertFalse(errorAlerts.isEmpty());
    }

    /*  OTHER METHODS  */

    private void saveNewCustomerToFile() { // to do: implement saving into Excel
        try {
            FileWriter file = new FileWriter("my_users.txt", true);
            BufferedWriter out = new BufferedWriter(file);
            out.write(emailInput.getAttribute("value") + "; " +
                    passwordInput.getAttribute("value") + "; " +
                    customerFirstNameInput.getAttribute("value") + "; " +
                    customerLastNameInput.getAttribute("value") + "; " +
                    new Select(birthdayDaySelect).getFirstSelectedOption().getAttribute("value") + "; " +
                    new Select(birthdayMonthSelect).getFirstSelectedOption().getAttribute("value") + "; " +
                    new Select(birthdayYearSelect).getFirstSelectedOption().getAttribute("value") + "\n");
            // to do: implement saving gender and newsletter
            out.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
