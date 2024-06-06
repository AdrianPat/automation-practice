package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.Random;

public class Home extends BasePage {

    public Home() {
        super();
    }

    @FindBy(css = "[title='Log in to your customer account']")
    private WebElement signInButton;

    @FindBy(id = "search_query_top")
    private WebElement searchInput;

    @FindBy(css = "[class='btn btn-default button-search']")
    private WebElement submitSearchButton;

    @Step
    public SignIn openSignInPage() {
        signInButton.click();
        // captureFullPageScreenshot();
        return new SignIn();
    }

    private void enterTextInSearchInput(String searchPhrase) {
        searchInput.sendKeys(searchPhrase);
    }

    @Step
    public Products submitSearch(String searchPhrase) {
        enterTextInSearchInput(searchPhrase);
        // captureFullPageScreenshot();
        if (new Random().nextBoolean()) {
            submitSearchButton.click();
        } else {
            searchInput.submit();
        }
        return new Products(searchPhrase);
    }
}
