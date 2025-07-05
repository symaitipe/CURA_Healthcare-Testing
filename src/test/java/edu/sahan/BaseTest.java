package edu.sahan;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import java.util.HashMap;
import java.util.Map;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {

    protected WebDriver driver;
    protected HomePage homePage;

    @BeforeAll
    public void goToAURAHealth() {
        // Set the path to EdgeDriver
        System.setProperty("webdriver.edge.driver", "src/test/resources/msedgedriver.exe");

        // Configure EdgeOptions
        EdgeOptions options = getEdgeOptions();

        // Initialize EdgeDriver
        driver = new EdgeDriver(options);

        // Navigate to the test site
        driver.get("https://katalon-demo-cura.herokuapp.com/");
        driver.manage().window().maximize();

        homePage = new HomePage(driver);
    }

    private static EdgeOptions getEdgeOptions() {
        EdgeOptions options = new EdgeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("autofill.profile_enabled", false);
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-extensions");
        return options;
    }

    @AfterAll
    public void quitDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}
