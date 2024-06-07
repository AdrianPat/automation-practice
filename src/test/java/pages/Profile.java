package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class Profile extends BasePage {

    private final String userName;

    /* public Profile() {
        super();
    } */

    public Profile(String userName) {
        super();
        this.userName = userName;
    }

    @FindBy(css = "#center_column > h1")
    private WebElement profilePageHeader;

    @FindBy(css = "[title='View my customer account']")
    private WebElement viewMyCustomerAccountButton;

    @FindBy(css = "[title='Log me out']")
    private WebElement signOutButton;

    private void isUserSignedIn() {
        Assert.assertEquals("MY ACCOUNT", profilePageHeader.getText());
        Assert.assertEquals(userName, viewMyCustomerAccountButton.getText());
    }

    @Step
    public void userShouldBeSuccessfullyRegistered() {
        isUserSignedIn();
    }

    @Step
    public void userShouldBeSuccessfullySignedIn() {
        isUserSignedIn();
    }

    @Step
    public SignIn signOut() {
        signOutButton.click();
        return new SignIn();
    }
}
