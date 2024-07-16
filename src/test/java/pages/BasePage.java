package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Random;

import static config.WebDriverSingleton.getDriver;
import static utilities.Action.waitForVisibility;

public abstract class BasePage {

    protected BasePage() {
        PageFactory.initElements(getDriver(), this);
        waitForVisibility(pageContent);
    }

    @FindBy(id = "columns") // id = "page"
    private WebElement pageContent;

    @FindBy(id = "search_query_top")
    protected WebElement searchInput;

    @FindBy(css = "[class='btn btn-default button-search']")
    protected WebElement submitSearchButton;

    @FindBy(css = "[title='Women'")
    protected WebElement womenTab;

    @FindBy(css = "[title='View my shopping cart']")
    protected WebElement cartButton;

    /*  GO TO TAB: WOMEN, DRESSES, T-SHIRTS, BLOG  */

    @Step
    public Products goToWomenTab() {
        womenTab.click();
        return new Products();
    }

    /*  PRODUCT SEARCH  */

    private void enterTextInSearchInput(String searchPhrase) {
        searchInput.sendKeys(searchPhrase);
    }

    protected void submitByEnterOrClick(String searchPhrase) {
        enterTextInSearchInput(searchPhrase);
        if (new Random().nextBoolean()) {
            submitSearchButton.click();
        } else {
            searchInput.submit();
        }
    }

    @Step
    public Products submitSearch(String searchPhrase) {
        submitByEnterOrClick(searchPhrase);
        return new Products(searchPhrase);
    }

    @Step
    protected Cart goToCart() {
        cartButton.click();
        return new Cart();
    }
}
