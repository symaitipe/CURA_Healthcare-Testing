package edu.sahan;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginResultPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public LoginResultPage (WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(4));
    }

    private static final By errorAlert = By.cssSelector("p[class='lead text-danger']");

    public boolean isLoginSuccessful() {
        try {
             return wait.until(ExpectedConditions.invisibilityOfElementLocated(errorAlert));
        } catch (TimeoutException e) {
            return false;
        }
    }

    public String getErrorMessage(){
        return driver.findElement(errorAlert).getText();
    }
}
