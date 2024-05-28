package utilities;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static config.WebDriverSingleton.getInstance;

public abstract class Actions {

    private final static WebDriverWait wait = new WebDriverWait(getInstance(), Duration.ofSeconds(10));

    public static void waitForVisibilityOfElement(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }
}
