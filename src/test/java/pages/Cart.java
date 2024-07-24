package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import java.util.List;

import static utilities.Action.waitForVisibility;

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

    @FindBy(css = "[title='Delete']")
    private List<WebElement> trashIcons;

    @FindBy(css = "[class='alert alert-warning']")
    private WebElement cartIsEmptyAlert;

    @FindBy(css = "[class='button btn btn-default standard-checkout button-medium']")
    private WebElement proceedToCheckoutButton;

    @FindBy(css = "[name='processAddress']")
    private WebElement processAddressButton;

    @FindBy(css = "[name='processCarrier']")
    private WebElement processCarrierButton;

    @FindBy(id = "cgv")
    private WebElement agreeToTheTermsCheckbox;

    @FindBy(css = "[class='bankwire']")
    private WebElement payByBankWireButton;

    @FindBy(css = "[class='button btn btn-default button-medium']")
    private WebElement confirmMyOrderButton;

    @FindBy(css = "[class='alert alert-success']")
    private WebElement successAlert;

    @FindBy(css = "[class='box']")
    private WebElement orderInfoBox;

    /*  CART  */

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
        waitForVisibility(cartIsEmptyAlert);
        Assert.assertEquals(cartIsEmptyAlert.getText(), "Your shopping cart is empty.");
    }

    /*  BUY THE PRODUCT(S)  */

    @Step
    public SignIn proceedToCheckout() {
        proceedToCheckoutButton.click();
        return new SignIn();
    }

    @Step
    public Cart processAddress() {
        processAddressButton.click();
        return this;
    }

    @Step
    public Cart processCarrier() {
        agreeToTheTermsCheckbox.click();
        processCarrierButton.click();
        return this;
    }

    @Step
    public Cart payByBankWire() {
        payByBankWireButton.click();
        return this;
    }

    @Step
    public Cart confirmOrder() {
        confirmMyOrderButton.click();
        return this;
    }

    @Step
    public void userShouldSeeSuccessAlertAndOrderInfoBox() {
        Assert.assertEquals(successAlert.getText(), "Your order on My Shop is complete.");
        Assert.assertTrue(orderInfoBox.isDisplayed());
        Assert.assertFalse(orderInfoBox.getText().isEmpty());
    }
}
