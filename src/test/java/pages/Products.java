package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;
import java.util.Random;

import static config.WebDriverSingleton.getInstance;

public class Products extends BasePage {

    private final String searchPhrase;

    /* public Products() {
        super();
    } */

    public Products(String searchPhrase) {
        super();
        this.searchPhrase = searchPhrase;
    }

    @FindBy(id = "search_query_top")
    private WebElement searchInput;

    @FindBy(css = "[class='btn btn-default button-search']")
    private WebElement submitSearchButton;

    @FindBy(css = "span[class='lighter']")
    private WebElement searchingTextInHeader;

    @FindBy(css = "span[class='heading-counter']")
    private WebElement resultsNumber;

    @FindBy(css = "p[class='alert alert-warning']")
    private WebElement noResultsAlert;

    @FindBy(css = "[class='product_list grid row']")
    private WebElement productsGrid;

    @FindBy(css = "ul[class='product_list grid row'] > li")
    private List<WebElement> productBlocksInGridView;

    @FindBy(css = "[class='product_list row list']")
    private WebElement productsList;

    @FindBy(css = "ul[class='product_list row list'] > li")
    private List<WebElement> productBlocksInListView;

    @FindBy(id = "list")
    private WebElement listViewIcon;

    @FindBy(id = "grid")
    private WebElement gridViewIcon;

    /*  SEARCHING PRODUCTS — HAPPY PATHS  */

    @Step
    public void userShouldSeeProductsGrid() {
        Assert.assertFalse(resultsNumber.getText().startsWith("0"));
        String expected = searchPhrase.toUpperCase();
        Assert.assertEquals(searchingTextInHeader.getText(), "\"" + expected + "\"");
        Assert.assertTrue(productsGrid.isDisplayed());
        Assert.assertFalse(productBlocksInGridView.isEmpty());
    }

    @Step
    public Products changeViewFromGridToList() {
        listViewIcon.click();
        return this;
    }

    @Step
    public void userShouldSeeProductsList() {
        Assert.assertFalse(resultsNumber.getText().startsWith("0"));
        String expected = searchPhrase.toUpperCase();
        Assert.assertEquals(searchingTextInHeader.getText(), "\"" + expected + "\"");
        Assert.assertTrue(productsList.isDisplayed());
        Assert.assertFalse(productBlocksInListView.isEmpty());
    }

    @Step
    public Products changeViewFromListToGrid() throws InterruptedException {
        listViewIcon.click();
        wait(1000);
        gridViewIcon.click();
        getInstance().navigate().refresh();
        return this;
    }

    @Step
    public Products nextSearchingOfProducts(String searchPhrase) {
        searchInput.clear();
        searchInput.sendKeys(searchPhrase);
        if (new Random().nextBoolean()) {
            submitSearchButton.click();
        } else {
            searchInput.submit();
        }
        return new Products(searchPhrase);
    }

    /*  SEARCHING PRODUCTS — NEGATIVE PATHS  */

    @Step
    public void userShouldSeeNoResultsAlert() {
        Assert.assertTrue(resultsNumber.getText().startsWith("0"));
        Assert.assertTrue(noResultsAlert.isDisplayed());
        Assert.assertTrue(noResultsAlert.getText().endsWith("\"" + searchPhrase + "\""));
    }

    @Step
    public void userShouldSeePleaseEnterASearchKeywordAlert() {
        Assert.assertTrue(resultsNumber.getText().startsWith("0"));
        Assert.assertTrue(noResultsAlert.isDisplayed());
        Assert.assertEquals(noResultsAlert.getText(), "Please enter a search keyword");
    }
}
