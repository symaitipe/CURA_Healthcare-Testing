// src/main/java/edu/sahan/HomePage.java
package edu.sahan;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class HomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final static By makeAppointmentButton = By.id("btn-make-appointment");
    private final static By burgerMenuButton = By.id("menu-toggle");
    private final static By loginMenuItem = By.cssSelector("a[href='profile.php#login']");
    private final static By logoutMenuItem = By.xpath("//a[text()='Logout']");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    public BurgerMenu goToBurgerMenu(){
        return new BurgerMenu(driver);
    }

    public boolean isUserLoggedIn() {
        // If logout menu item is visible, user is logged in
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(logoutMenuItem)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLoginMenuItemVisible() {
        try {
            driver.findElement(burgerMenuButton).click();
            boolean visible = wait.until(ExpectedConditions.visibilityOfElementLocated(loginMenuItem)).isDisplayed();
            driver.findElement(burgerMenuButton).click();
            return visible;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isBurgerMenuCollapsed() {
        // Check if the burger menu is collapsed (not expanded)
        try {
            // If login/logout menu item is not visible, menu is collapsed
            return driver.findElement(loginMenuItem).isDisplayed() && !driver.findElement(logoutMenuItem).isDisplayed();
        } catch (Exception e) {
            return true;
        }
    }

    public AppointmentForm goToMakeAppointmentPage() {
        wait.until(ExpectedConditions.elementToBeClickable(makeAppointmentButton)).click();
        return new AppointmentForm(driver);
    }
}
