package edu.sahan;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class HistoryPage {
    private final WebDriver driver;

    public HistoryPage(WebDriver driver) {
        this.driver = driver;
    }

    // === Static Locators for fields inside a single appointment panel ===
    private final By facility = By.id("facility");
    private final By readmission = By.id("hospital_readmission");
    private final By program = By.id("program");
    private final By comment = By.id("comment");

    // === Dynamic Locator for appointment panels by date ===
    private By appointmentPanelByDate(String date) {
        return By.xpath("//div[text()='" + date + "']/ancestor::div[contains(@class, 'panel')]");
    }



    public List<WebElement> findAllAppointmentsByDate(String date) {
        return driver.findElements(appointmentPanelByDate(date));
    }

    public String getFacility(WebElement appointment) {
        return appointment.findElement(facility).getText();
    }

    public String getReadmission(WebElement appointment) {
        return appointment.findElement(readmission).getText();
    }

    public String getProgram(WebElement appointment) {
        return appointment.findElement(program).getText();
    }

    public String getComment(WebElement appointment) {
        return appointment.findElement(comment).getText();
    }
}
