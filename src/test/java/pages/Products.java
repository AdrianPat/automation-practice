package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;

import static config.WebDriverSingleton.getDriver;

public class Products extends BasePage {

    private String searchPhrase;

    public Products() {
        super();
    }

    public Products(String searchPhrase) {
        super();
        this.searchPhrase = searchPhrase;
    }

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

    @FindBy(css = "[class='breadcrumb clearfix']")
    private WebElement breadcrumb;

    @FindBy(css = "[class='cat-name']")
    private WebElement categoryNameInHeading;

    @FindBy(css = "[class='available-dif']")
    private List<WebElement> availableProductsLabels;

    @FindBy(css = "[style='display: block;'] > [class='button lnk_view btn btn-default']")
    private WebElement moreButton;

    /*  PRODUCT SEARCH — POSITIVE TESTING  */

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
        getDriver().navigate().refresh();
        return this;
    }

    @Step
    public Products nextSearchingOfProducts(String searchPhrase) {
        searchInput.clear();
        submitByEnterOrClick(searchPhrase);
        return new Products(searchPhrase);
    }

    /*  PRODUCT SEARCH — NEGATIVE TESTING  */

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

    /*  ADD PRODUCT TO CART — POSITIVE TESTING  */

    @Step
    public ProductDetails goToProductDetails() {
        Actions actions = new Actions(getDriver());
        actions.moveToElement(availableProductsLabels.get(0)).perform();
        actions.click(moreButton).perform();
        return new ProductDetails();
    }

    /*  GO TO TAB: WOMEN, DRESSES, T-SHIRTS, BLOG  */

    @Step
    public void userShouldSeeProductsInWomenTab() {
        Assert.assertEquals(breadcrumb.getText(), "> Women");
        Assert.assertEquals(categoryNameInHeading.getText(), "WOMEN ");
        Assert.assertTrue(productsGrid.isDisplayed());
        Assert.assertFalse(productBlocksInGridView.isEmpty());
    }
}
