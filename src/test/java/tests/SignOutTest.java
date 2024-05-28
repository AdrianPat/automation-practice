package tests;

import config.TestConfig;
import org.testng.annotations.Test;
import pages.Home;

public class SignOutTest extends TestConfig {

    @Test
    public void successfulSingOut() {
        new Home()
                .openSignInPage()
                .submitSignIn()
                .signOut()
                .userShouldBeSuccessfullySignedOut();
    }
}
