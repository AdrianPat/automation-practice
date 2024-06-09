package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

import static config.WebDriverSingleton.getDriver;

public class ProductDetails extends BasePage {

    public ProductDetails() {
        super();
    }

    @FindBy(id = "availability_value")
    private WebElement availabilityStatus;

    @FindBy(id = "group_1")
    private WebElement sizeSelect;

    @FindBy(css = "ul[id='color_to_pick_list'] > li")
    private List<WebElement> colorPickList;

    @FindBy(id = "add_to_cart")
    private WebElement addToCartButton;

    @FindBy(css = "[class='clearfix']")
    private WebElement productAddedToCartPopUp;

    @FindBy(css = "[class='layer_cart_product col-xs-12 col-md-6'] > h2")
    private WebElement productAddedToCartPopUpHeader;

    private void findAvailableProductVariant() {
        int sizes = new Select(sizeSelect).getOptions().size();
        outerLoop:
        for (int size = 1; size <= sizes; size++) {
            new Select(sizeSelect).selectByValue(String.valueOf(size));
            for (WebElement color : colorPickList) {
                color.click();
                if (availabilityStatus.getText().equals("In stock")) {
                    break outerLoop;
                }
            }
        }
    }

    @Step
    public ProductDetails addToCart() {
        findAvailableProductVariant();
        addToCartButton.click();
        return this;
    }

    @Step
    public void userShouldSeePopUpThatProductIsAddedToCart() {
        new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOf(productAddedToCartPopUp));
        String expected = "Product successfully added to your shopping cart";
        Assert.assertEquals(productAddedToCartPopUpHeader.getText(), expected);
    }
}
