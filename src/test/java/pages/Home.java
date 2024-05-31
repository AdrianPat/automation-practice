package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.allure.annotations.Step;

public class Home extends BasePage {

    public Home() {
        super();
    }

    @FindBy(css = "[title=\"Log in to your customer account\"]")
    private WebElement signInButton;

    @Step
    public SignIn openSignInPage() {
        signInButton.click();
        // captureFullPageScreenshot();
        return new SignIn();
    }
}
