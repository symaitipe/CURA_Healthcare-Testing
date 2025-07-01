package edu.sahan;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {

    private WebDriver driver;
    private WebDriverWait wait;
    protected HomePage homePage;

    @BeforeAll
    public void goToAURAHealth(){

        System.setProperty("webdriver.chrome.driver","src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.get("https://katalon-demo-cura.herokuapp.com/");
        driver.manage().window().fullscreen();

        homePage = new HomePage(driver);

    }


    @AfterAll
    public void quitDriver(){
        driver.quit();
    }

}
