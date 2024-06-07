package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static config.WebDriverSingleton.getDriver;
import static utilities.Actions.waitForVisibilityOfElement;

public abstract class BasePage {

    public BasePage() {
        PageFactory.initElements(getDriver(), this);
        waitForVisibilityOfElement(pageContent);
    }

    @FindBy(id = "columns")
    private WebElement pageContent;
}
