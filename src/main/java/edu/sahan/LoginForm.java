package edu.sahan;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginForm {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public LoginForm(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }


    private final static By userNameField = By.id("txt-username");
    private final static By passwordField = By.id("txt-password");
    private final static By submitButton = By.id("btn-login");


    // Base method for login actions
    private void login(String username, String password) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(userNameField)).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(submitButton).click();
    }

    // Successful login
    public LoginResultPage loginWithValidCredentials(String username, String password) {
        login(username, password);
        return new LoginResultPage(driver);
    }

    // Failed login attempts
    public LoginResultPage loginWithInvalidCredentials(String username, String password) {
        login(username, password);
        return new LoginResultPage(driver);
    }

    public LoginResultPage loginWithEmptyUsername(String password) {
        login("", password);
        return new LoginResultPage(driver);
    }

    public LoginResultPage loginWithEmptyPassword(String username) {
        login(username, "");
        return new LoginResultPage(driver);
    }

    public LoginResultPage loginWithEmptyCredentials() {
        login("", "");
        return new LoginResultPage(driver);
    }
}
