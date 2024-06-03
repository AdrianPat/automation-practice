package pages;

import enums.Email;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import utilities.DataFaker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import static utilities.AlertMessageContent.getAlertContent;
import static utilities.Screenshots.captureFullPageScreenshot;

public class SignIn extends BasePage {

    public SignIn() {
        super();
    }

    private final DataFaker faker = new DataFaker();

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "passwd")
    private WebElement passwordInput;

    @FindBy(id = "SubmitLogin")
    private WebElement signInButton;

    @FindBy(id = "email_create")
    private WebElement emailCreateInput;

    @FindBy(id = "SubmitCreate")
    private WebElement createAccountButton;

    @FindBy(css = "[class='page-heading']")
    private WebElement authenticationHeadingText;

    @FindBy(css = "[class='alert alert-danger']")
    private WebElement signInAlertBlock;

    @FindBy(xpath = "//div[@class='alert alert-danger']/ol/li")
    private List<WebElement> createAccountError;

    /*  SIGN IN — HAPPY PATH  */

    private String fillInSignInForm() {
        String[] userInfo = getRandomUserFromFile();
        emailInput.sendKeys(userInfo[0]);
        passwordInput.sendKeys(userInfo[1]);
        return userInfo[2] + " " + userInfo[3];
    }

    @Step
    public Profile submitSignIn() {
        String userName = fillInSignInForm();
        captureFullPageScreenshot();
        signInButton.click();
        return new Profile(userName);
    }

    /*  SIGN IN — NEGATIVE PATH  */

    private void fillInSignInFormWithInvalidData(String email, String password) {
        emailInput.sendKeys(email);
        passwordInput.sendKeys(password);
    }

    @Step
    public SignIn submitSignInWithInvalidData(String email, String password) {
        fillInSignInFormWithInvalidData(email, password);
        captureFullPageScreenshot();
        signInButton.click();
        return new SignIn();
    }

    @Step
    public void submitSignInWithInvalidDataShouldFail() {
        Assert.assertTrue(authenticationHeadingText.isDisplayed());
        Assert.assertTrue(signInAlertBlock.isDisplayed());
    }

    /*  MOVING TO SIGN UP (REGISTRATION) PAGE — HAPPY PATH  */

    private void fillInCreateAccountForm(String emailAddress) {
        emailCreateInput.sendKeys(emailAddress);
    }

    @Step
    public SignUp submitCreateAccountForm() {
        fillInCreateAccountForm(faker.getFakeEmail());
        captureFullPageScreenshot();
        createAccountButton.click();
        return new SignUp();
    }

    /*  MOVING TO SIGN UP (REGISTRATION) PAGE — NEGATIVE PATH  */

    @Step
    public SignIn submitCreateAccountFormWithInvalidEmail(String invalidEmail) {
        fillInCreateAccountForm(invalidEmail);
        captureFullPageScreenshot();
        createAccountButton.click();
        return this;
    }

    @Step
    public SignIn submitCreateAccountFormWithTakenEmail() {
        fillInCreateAccountForm(getRandomUserFromFile()[0]);
        captureFullPageScreenshot();
        createAccountButton.click();
        return this;
    }

    @Step
    public void userShouldSeeCreateAccountAlertMessage(Email email) {
        switch (email) {
            case INVALID:
                String invalidEmail = "Invalid email address.";
                Assert.assertListContainsObject(getAlertContent(createAccountError),
                        invalidEmail, "User should see alert: " + invalidEmail);
                break;
            case TAKEN:
                String takenEmail = "An account using this email address has already been " +
                        "registered. Please enter a valid password or request a new one.";
                Assert.assertListContainsObject(getAlertContent(createAccountError),
                        takenEmail, "User should see alert: " + takenEmail);
                break;
        }
    }

    /*  ASSERTION FOR SIGNING OUT  */

    @Step
    public void userShouldBeSuccessfullySignedOut() {
        Assert.assertTrue(authenticationHeadingText.isDisplayed());
    }

    /*  OTHER METHODS  */

    private String[] getRandomUserFromFile() { // to do: implement getting user from Excel (saving: in SignUp class)
        String[] userInfo = {};
        try {
            List<String> file = Files.readAllLines(Paths.get("my_users.txt"));
            userInfo = file.get(new Random().nextInt(file.size() - 1)).split("; ");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Probably the file “my_users.txt” does not exist or is empty.");
        }
        return userInfo;
    }
}
