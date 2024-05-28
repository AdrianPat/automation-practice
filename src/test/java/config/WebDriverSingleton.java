package config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public abstract class WebDriverSingleton {

    // Ta klasa pilnuje, aby był uruchomiony tylko 1 WebDriver

    private static WebDriver driver;

    private WebDriverSingleton() {
    }

    public static WebDriver getInstance() { // pozwala na pobranie instancji drivera
        if (driver == null) {
            WebDriverManager.chromedriver().setup(); // WebDriverManager zawiera też drivery innych przeglądarek
            driver = new ChromeDriver();
        }
        return driver;
    }

    public static void driverQuit() {
        if (driver != null) {
            driver.quit();
        }
        driver = null;
    }
}
