package config;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

import static config.WebDriverSingleton.driverQuit;
import static config.WebDriverSingleton.getInstance;
import static utilities.Screenshots.captureScreenshot;

public abstract class TestConfig {

    @BeforeMethod
    public void driverSetup() {
        WebDriver driver = getInstance();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("http://www.automationpractice.pl/index.php");
    }

    @AfterMethod
    public void driverTearDown() {
        captureScreenshot();
        driverQuit();
    }
}
