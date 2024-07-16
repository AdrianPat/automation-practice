package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;

public class Cart extends BasePage {

    private int quantity;

    public Cart() {
        super();
    }

    public Cart(int quantity) {
        super();
        this.quantity = quantity;
    }

    @FindBy(css = "[class='cart_quantity_input form-control grey']")
    private WebElement quantityInput;

    @FindBy(className = "icon-trash")
    private List<WebElement> trashIcons;

    @FindBy(className = "alert alert-warning")
    private WebElement cartIsEmptyAlert;

    @Step
    public void userShouldSeeCorrectQuantityInCart() {
        Assert.assertEquals(quantityInput.getAttribute("value"), String.valueOf(quantity));
    }

    @Step
    public Cart removeProductFromCart() {
        trashIcons.get(0).click();
        return this;
    }

    @Step
    public void userShouldSeeEmptyCart() {
        Assert.assertEquals(cartIsEmptyAlert.getText(), "Your shopping cart is empty.");
    }
}
