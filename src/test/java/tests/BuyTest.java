package tests;

import config.TestConfig;
import org.testng.annotations.Test;
import pages.Home;

public class BuyTest extends TestConfig {

    @Test
    public void successfulBuyProduct() {
        new Home()
                .goToWomenTab()
                .goToProductDetails()
                .addToCart()
                .goToCartAfterAddProductToCart()
                .proceedToCheckout()
                .signInToOrder()
                .processAddress()
                .processCarrier()
                .payByBankWire()
                .confirmOrder()
                .userShouldSeeSuccessAlertAndOrderInfoBox();
    }
}
