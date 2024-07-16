package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Home extends BasePage {

    public Home() {
        super();
    }

    @FindBy(css = "[title='Log in to your customer account']")
    private WebElement signInButton;

    @Step
    public SignIn openSignInPage() {
        signInButton.click();
        return new SignIn();
    }
}
