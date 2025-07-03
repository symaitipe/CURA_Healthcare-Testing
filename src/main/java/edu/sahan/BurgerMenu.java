package edu.sahan;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BurgerMenu {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public BurgerMenu(WebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private static final By burgerMenu = By.id("menu-toggle");

    private static final By logoutButton = By.xpath("//a[text()='Logout']");

    private static final By loginButton = By.cssSelector("a[href='profile.php#login']");


    public LoginForm goToLoginPage(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(burgerMenu)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(loginButton)).click();
        return new LoginForm(driver);
    }


    public void logOut(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(burgerMenu)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(logoutButton)).click();
    }



}
