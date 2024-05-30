package pages;

import org.hamcrest.core.IsCollectionContaining;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import ru.yandex.qatools.allure.annotations.Step;
import utilities.DataFaker;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static utilities.AlertMessageContent.getAlertContent;
import static utilities.Screenshots.captureScreenshot;

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

    @FindBy(css = "[type=\"radio\"]")
    private List<WebElement> gendersRadioButtons;

    /*  REGISTRATION — HAPPY PATH  */

    private void fillInRegistrationForm() { // ADAPT TO ACTUAL FIELD REQUIRES ##############
        gendersRadioButtons.get(new Random().nextInt(gendersRadioButtons.size())).click();
        customerFirstNameInput.sendKeys(faker.getFakeFirstName());
        customerLastNameInput.sendKeys(faker.getFakeLastName());
        passwordInput.sendKeys(faker.getFakePassword());
        Date fakeDate = faker.getFakeDateOfBirthday();
        new Select(birthdayDaySelect).selectByValue(String.valueOf(fakeDate.getDate()));
        new Select(birthdayMonthSelect).selectByValue(String.valueOf(fakeDate.getMonth() + 1));
        new Select(birthdayYearSelect).selectByValue(String.valueOf(fakeDate.getYear() + 1900));
        if (new Random().nextBoolean()) newsletterCheckbox.click();
    }

    @Step
    public Profile submitRegistrationForm() {
        fillInRegistrationForm();
        saveNewCustomerToFile();
        captureScreenshot();
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
        Assert.assertThat(errors, IsCollectionContaining.hasItem("lastname is required."));
        Assert.assertThat(errors, IsCollectionContaining.hasItem("firstname is required."));
        Assert.assertThat(errors, IsCollectionContaining.hasItem("passwd is required."));
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
        captureScreenshot();
        registrationButton.click();
        return this;
    }

    @Step
    public void userShouldSeeRegistrationFormAlert() {
        Assert.assertFalse(errorAlerts.isEmpty());
    }

    /*  OTHER METHODS  */

    private void saveNewCustomerToFile() {
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
            // implement saving gender and newsletter
            out.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
