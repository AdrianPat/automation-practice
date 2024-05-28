package config;

//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

import static config.WebDriverSingleton.driverQuit;
import static config.WebDriverSingleton.getInstance;
import static utilities.Screenshots.captureScreenshot;

public abstract class TestConfig {

    private WebDriver driver;

    @BeforeMethod
    public void driverSetup() {
        String baseURL = "http://www.automationpractice.pl/index.php";
        driver = getInstance();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // działa na całe skrypty, aż znajdzie poszczególne elementy
        driver.get(baseURL);
    }

    @AfterMethod
    public void driverTearDown() {
        captureScreenshot();
        driverQuit();
    }
}
