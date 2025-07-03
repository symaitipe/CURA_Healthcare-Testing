package edu.sahan;


import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.chrome.ChromeOptions;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {

    protected WebDriver driver;
    protected HomePage homePage;

    @BeforeAll
    public void goToAURAHealth(){

        System.setProperty("webdriver.chrome.driver","src/test/resources/chromedriver.exe");

        // Disable password manager in Chrome
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-save-password-bubble");
        options.setExperimentalOption("prefs", new java.util.HashMap<String, Object>() {{
            put("credentials_enable_service", false);
            put("profile.password_manager_enabled", false);
        }});

        driver = new ChromeDriver(options);

        driver.get("https://katalon-demo-cura.herokuapp.com/");
        driver.manage().window().fullscreen();

        homePage = new HomePage(driver);

    }


    @AfterAll
    public void quitDriver(){
        driver.quit();
    }

}
