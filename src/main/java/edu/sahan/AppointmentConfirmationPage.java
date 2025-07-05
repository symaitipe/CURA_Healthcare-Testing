package edu.sahan;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AppointmentConfirmationPage {

    private final static By appointmentConfirmation = By.xpath("//h2[text()='Appointment Confirmation']");

    WebDriver driver;
    WebDriverWait wait;

    public AppointmentConfirmationPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public boolean isAppointmentConfirmed() {
        try {
            return wait.until(d -> d.findElement(appointmentConfirmation).isDisplayed());
        } catch (Exception e) {
            return false;
        }
    }

    public HistoryPage goToHistoryPage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("menu-toggle"))).click();
        driver.findElement(By.cssSelector("a[href='history.php#history']")).click();
        return new HistoryPage(driver);
    }
}
