package tests;

import config.TestConfig;
import org.testng.annotations.Test;
import pages.Home;

public class CartTest extends TestConfig {

    @Test
    public void successfulAddProductToCart() {
        new Home()
                .goToWomenTab()
                .goToProductDetails()
                .addToCart()
                .userShouldSeePopUpThatProductIsAddedToCart();
    }

    // TODO: test of number of added products to cart

    // TODO: test of removing products from cart

    // TODO: test of adding 2 different products
}
