package pages;

import enums.Email;
import org.hamcrest.core.IsCollectionContaining;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import ru.yandex.qatools.allure.annotations.Step;
import utilities.DataFaker;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import static utilities.AlertMessageContent.getAlertContent;
import static utilities.Screenshots.captureScreenshot;

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

    @FindBy(css = "[class=\"page-heading\"]")
    private WebElement authenticationHeadingText;

    @FindBy(css = "[class=\"alert alert-danger\"]")
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
        captureScreenshot();
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
        captureScreenshot();
        signInButton.click();
        return new SignIn();
    }

    @Step
    public void submitSignWithInvalidDataShouldFail() {
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
        captureScreenshot();
        createAccountButton.click();
        return new SignUp();
    }

    /*  MOVING TO SIGN UP (REGISTRATION) PAGE — NEGATIVE PATH  */

    private void fillInCreateAccountFormWithInvalidEmail(String invalidEmail) {
        emailCreateInput.sendKeys(invalidEmail);
    }

    @Step
    public SignIn submitCreateAccountFormWithInvalidEmail(String invalidEmail) {
        fillInCreateAccountFormWithInvalidEmail(invalidEmail);
        captureScreenshot();
        createAccountButton.click();
        return this;
    }

    @Step
    public SignIn submitCreateAccountFormWithTakenEmail() {
        fillInCreateAccountFormWithInvalidEmail(getRandomUserFromFile()[0]);
        createAccountButton.click();
        return this;
    }

    @Step
    public void userShouldSeeCreateAccountAlertMessage(Email email) {
        switch (email) {
            case INVALID:
                org.junit.Assert.assertThat(getAlertContent(createAccountError),
                        IsCollectionContaining.hasItem("Invalid email address."));
                break;
            case TAKEN:
                org.junit.Assert.assertThat(getAlertContent(createAccountError),
                        IsCollectionContaining.hasItem("An account using this email address has already been " +
                                "registered. Please enter a valid password or request a new one."));
                break;
        }
    }

    /*  ASSERTION FOR SIGNING OUT  */

    @Step
    public void userShouldBeSuccessfullySignedOut() {
        Assert.assertTrue(authenticationHeadingText.isDisplayed());
    }

    /*  OTHER METHODS  */

    private String[] getRandomUserFromFile() {
        String[] userInfo = {};
        try {
            List<String> file = Files.readAllLines(Paths.get("my_users.txt"));
            userInfo = file.get(new Random().nextInt(file.size() - 1)).split("; ");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Probably the file \"my_users.txt\" does not exist or is empty.");
        }
        return userInfo;
    }
}
