package pages;

import enums.Email;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import utilities.DataFaker;

import java.util.List;

import static utilities.AlertMessageContent.getAlertContent;
import static utilities.Excel.getRandomUserFromExcel;

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

    @FindBy(css = "[class='alert alert-danger'] > ol > li")
    private List<WebElement> createAccountError;

    /*  SIGN IN — POSITIVE TESTING  */

    private String fillInSignInForm() {
        String[] userInfo = getRandomUserFromExcel();
        emailInput.sendKeys(userInfo[0]);
        passwordInput.sendKeys(userInfo[1]);
        return userInfo[2] + " " + userInfo[3];
    }

    @Step
    public Profile submitSignIn() {
        String userName = fillInSignInForm();
        signInButton.click();
        return new Profile(userName);
    }

    @Step
    public Cart signInToOrder() {
        emailInput.sendKeys("testowy_e-mail@test.pl");
        passwordInput.sendKeys("Password123!");
        signInButton.click();
        return new Cart();
    }

    /*  SIGN IN — NEGATIVE TESTING  */

    private void fillInSignInFormWithInvalidData(String email, String password) {
        emailInput.sendKeys(email);
        passwordInput.sendKeys(password);
    }

    @Step
    public SignIn submitSignInWithInvalidData(String email, String password) {
        fillInSignInFormWithInvalidData(email, password);
        signInButton.click();
        return new SignIn();
    }

    @Step
    public void submitSignInWithInvalidDataShouldFail() {
        Assert.assertTrue(authenticationHeadingText.isDisplayed());
        Assert.assertTrue(signInAlertBlock.isDisplayed());
    }

    /*  MOVE TO SIGN UP (REGISTRATION) PAGE — POSITIVE TESTING  */

    private void fillInCreateAccountForm(String emailAddress) {
        emailCreateInput.sendKeys(emailAddress);
    }

    @Step
    public SignUp submitCreateAccountForm() {
        fillInCreateAccountForm(faker.getFakeEmail());
        createAccountButton.click();
        return new SignUp();
    }

    /*  MOVE TO SIGN UP (REGISTRATION) PAGE — NEGATIVE TESTING  */

    @Step
    public SignIn submitCreateAccountFormWithInvalidEmail(String invalidEmail) {
        fillInCreateAccountForm(invalidEmail);
        createAccountButton.click();
        return this;
    }

    @Step
    public SignIn submitCreateAccountFormWithTakenEmail() {
        fillInCreateAccountForm(getRandomUserFromExcel()[0]);
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

    /* private String[] getRandomUserFromFile() { // method no longer used
        String[] userInfo = {};
        try {
            List<String> file = Files.readAllLines(Paths.get("my_users.txt"));
            userInfo = file.get(new Random().nextInt(file.size() - 1)).split("; ");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Probably the file “my_users.txt” does not exist or is empty.");
        }
        return userInfo;
    } */
}
