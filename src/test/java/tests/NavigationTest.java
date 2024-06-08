package tests;

import config.TestConfig;
import org.testng.annotations.Test;
import pages.Home;

public class NavigationTest extends TestConfig {

    @Test
    public void successfulGoingToWomenTab() {
        new Home()
                .goToWomenTab()
                .userShouldSeeProductsInWomenTab();
    }
}
