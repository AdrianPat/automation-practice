package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

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

    @Step
    public void userShouldSeeCorrectQuantityInCart() {
        Assert.assertEquals(quantityInput.getAttribute("value"), String.valueOf(quantity));
    }
}
