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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static utilities.Screenshots.captureScreenshot;

public class SignUp extends BasePage {

    public SignUp() {
        super();
    }

    private final DataFaker faker = new DataFaker();

    @FindBy(id = "customer_firstname")
    private WebElement customerFirstnameInput;

    @FindBy(id = "customer_lastname")
    private WebElement customerLastnameInput;

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

    /* @FindBy(id = "address1")
    private WebElement addressLineInput;

    @FindBy(id = "city")
    private WebElement cityInput;

    @FindBy(id = "id_state")
    private WebElement stateSelect;

    @FindBy(id = "postcode")
    private WebElement zipCodeInput;

    @FindBy(id = "phone_mobile")
    private WebElement phoneNumberInput; */

    @FindBy(id = "submitAccount")
    private WebElement submitFormButton;

    @FindBy(css = "#center_column > .alert li")
    private List<WebElement> alertMessageContent;

    @FindBy(css = "[type=\"radio\"]")
    private List<WebElement> gendersRadioButtons;

    private void fillInRegistrationForm(boolean validForm) {
        gendersRadioButtons.get(new Random().nextInt(gendersRadioButtons.size())).click();
        if (validForm) {
            customerFirstnameInput.sendKeys(faker.getFakeFirstname());
        }
        customerLastnameInput.sendKeys(faker.getFakeLastname());
        passwordInput.sendKeys(faker.getFakePassword());
        Date fakeDate = faker.getFakeDateOfBirthday();
        new Select(birthdayDaySelect).selectByValue(String.valueOf(fakeDate.getDate()));
        new Select(birthdayMonthSelect).selectByValue(String.valueOf(fakeDate.getMonth() + 1));
        new Select(birthdayYearSelect).selectByValue(String.valueOf(fakeDate.getYear() + 1900));
        if (new Random().nextBoolean()) newsletterCheckbox.click();
    }

    @Step
    public Profile submitFormWithValidData() {
        fillInRegistrationForm(true);
        saveNewCustomerToFile();
        captureScreenshot();
        submitFormButton.click();
        return new Profile();
    }

    @Step
    public SignUp submitFormWithInvalidData() {
        fillInRegistrationForm(false);
        captureScreenshot();
        submitFormButton.click();
        return this;
    }

    @Step
    public void userShouldSeeRegistrationFormAlert() {
        String EXPECTED_MESSAGE = "firstname is required.";
        Assert.assertThat(getAlertMessageContent(), IsCollectionContaining.hasItem(EXPECTED_MESSAGE));
    }

    private List<String> getAlertMessageContent() {
        List<String> alertMessages = new ArrayList<>();
        for (WebElement message : alertMessageContent) {
            alertMessages.add(message.getText());
        }
        return alertMessages;
    }

    private void saveNewCustomerToFile() {
        try {
            FileWriter file = new FileWriter("my_users.txt", true);
            BufferedWriter out = new BufferedWriter(file);
            out.write(emailInput.getAttribute("value") + "; " +
                    passwordInput.getAttribute("value") + "; " +
                    customerFirstnameInput.getAttribute("value") + "; " +
                    customerLastnameInput.getAttribute("value") + "; " +
                    new Select(birthdayDaySelect).getFirstSelectedOption().getAttribute("value") + "; " +
                    new Select(birthdayMonthSelect).getFirstSelectedOption().getAttribute("value") + "; " +
                    new Select(birthdayYearSelect).getFirstSelectedOption().getAttribute("value") + "\n");
            // dodać zapisywanie płci i czy newsletter
            out.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
