package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import java.util.List;
import java.util.Random;

import static config.WebDriverSingleton.getDriver;
import static utilities.Action.waitForVisibility;

public class ProductDetails extends BasePage {

    private int quantity;

    public ProductDetails() {
        super();
    }

    public ProductDetails(int quantity) {
        super();
        this.quantity = quantity;
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

    @FindBy(id = "quantity_wanted")
    private WebElement quantityInput;

    @FindBy(css = "[class='btn btn-default button-plus product_quantity_up']")
    private WebElement plusButton;

    @FindBy(id = "layer_cart_product_quantity")
    private WebElement quantityInPopUp;

    @FindBy(css = "[title='Proceed to checkout']")
    private WebElement proceedToCheckoutButton;

    @FindBy(css = "[class='cross']")
    private WebElement closeWindowButton;

    /*  ADD ONE PRODUCT TO CART (QUANTITY: 1)  */

    @Step
    public ProductDetails addToCart() {
        findAvailableProductVariant();
        waitForVisibility(addToCartButton);
        Actions actions = new Actions(getDriver());
        actions.moveToElement(addToCartButton).perform();
        actions.click(addToCartButton).perform();
        return this;
    }

    @Step
    public void userShouldSeePopUpThatProductIsAddedToCart() {
        waitForVisibility(productAddedToCartPopUp);
        String expected = "Product successfully added to your shopping cart";
        Assert.assertEquals(productAddedToCartPopUpHeader.getText(), expected);
    }

    /*  ADD ONE PRODUCT TO CART (QUANTITY: MANY)  */

    @Step
    public ProductDetails addManyProductsToCart() {
        findAvailableProductVariant();
        waitForVisibility(quantityInput);
        int quantity = new Random().nextInt(5) + 1;
        if (new Random().nextBoolean()) {
            quantityInput.clear();
            quantityInput.sendKeys(String.valueOf(quantity));
        } else {
            for (int i = 1; i < quantity; i++) {
                plusButton.click();
            }
        }
        addToCartButton.click();
        waitForVisibility(productAddedToCartPopUp);
        return new ProductDetails(quantity);
    }

    @Step
    public void userShouldSeeCorrectQuantityInPopUp() {
        waitForVisibility(productAddedToCartPopUp);
        Assert.assertEquals(quantityInPopUp.getText(), String.valueOf(quantity));
    }

    @Step
    public Cart goToCartAfterAddProductToCart() {
        waitForVisibility(productAddedToCartPopUp);
        proceedToCheckoutButton.click();
        cartButton.click();
        return new Cart(quantity);
    }

    /*  OTHER METHODS  */

    private void findAvailableProductVariant() {
        int sizes = new Select(sizeSelect).getOptions().size();
        outerLoop:
        for (int i = 1; i <= sizes; i++) {
            new Select(sizeSelect).selectByValue(String.valueOf(i));
            for (WebElement color : colorPickList) {
                color.click();
                if (availabilityStatus.getText().equals("In stock")) {
                    break outerLoop;
                }
            }
        }
    }

    @Step
    public Cart goToCartFromProductDetails() {
        waitForVisibility(closeWindowButton);
        closeWindowButton.click();
        cartButton.click();
        return new Cart();
    }
}
