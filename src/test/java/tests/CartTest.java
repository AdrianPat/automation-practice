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

    @Test
    public void successfulAddManyProductsToCart_CorrectQuantityInPopUp() {
        new Home()
                .goToWomenTab()
                .goToProductDetails()
                .addManyProductsToCart()
                .userShouldSeeCorrectQuantityInPopUp();
    }

    @Test
    public void successfulAddManyProductsToCart_CorrectQuantityInCart() {
        new Home()
                .goToWomenTab()
                .goToProductDetails()
                .addManyProductsToCart()
                .goToCartAfterAddProductToCart()
                .userShouldSeeCorrectQuantityInCart();
    }

    @Test
    public void successfulRemoveProductFromCart() {
        new Home()
                .goToWomenTab()
                .goToProductDetails()
                .addToCart()
                .goToCartFromProductDetails()
                .removeProductFromCart()
                .userShouldSeeEmptyCart();
    }

    // TODO: test of adding 2 different products
}
