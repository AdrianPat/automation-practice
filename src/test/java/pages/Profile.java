package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import ru.yandex.qatools.allure.annotations.Step;

public class Profile extends BasePage {

    private String userName;

    public Profile() {
        super();
    }

    public Profile(String userName) {
        super();
        this.userName = userName;
    }

    @FindBy(css = "#center_column > h1")
    private WebElement profilePageHeader;

    @FindBy(css = "[title=\"View my customer account\"]")
    private WebElement viewMyCustomerAccountButton;

    @FindBy(css = "[title=\"Log me out\"]")
    private WebElement signOutButton;

    @Step
    public void userShouldBeSuccessfullyRegistered() {
        Assert.assertEquals("MY ACCOUNT", profilePageHeader.getText());
    }

    @Step
    public void userShouldBeSuccessfullySignedIn() {
        userShouldBeSuccessfullyRegistered();
        Assert.assertEquals(userName, viewMyCustomerAccountButton.getText());
    }

    @Step
    public SignIn signOut() {
        signOutButton.click();
        return new SignIn();
    }
}
