package edu.sahan;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AppointmentForm {
    WebDriver driver;
    WebDriverWait wait;

    public AppointmentForm(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    // Locators
    private static final By facilityDropdown = By.id("combo_facility");
    private static final By chkHospitalReadmission = By.id("chk_hospotal_readmission");
    private static final By txtVisitDate = By.id("txt_visit_date");
    private static final By txtComment = By.id("txt_comment");
    private static final By btnBookAppointment = By.id("btn-book-appointment");

    private static final By radioMedicare = By.id("radio_program_medicare");
    private static final By radioMedicaid = By.id("radio_program_medicaid");
    private static final By radioNone = By.id("radio_program_none");

    // Helper: Wait until element is clickable
    private WebElement waitUntilClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    // Facility
    public void selectFacility(String facility) {
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(facilityDropdown));
        new Select(dropdown).selectByVisibleText(facility);
    }

    // Hospital readmission checkbox
    public void setHospitalReadmission(boolean apply) {
        WebElement checkbox = waitUntilClickable(chkHospitalReadmission);
        if (checkbox.isSelected() != apply) {
            checkbox.click();
        }
    }

    // Visit date
    public void setVisitDate(String date) {
        WebElement dateInput = waitUntilClickable(txtVisitDate);
        dateInput.clear();
        dateInput.sendKeys(date);

    }

    // Comment
    public void setComment(String comment) {
        WebElement commentBox = waitUntilClickable(txtComment);
        commentBox.clear();
        commentBox.sendKeys(comment);
    }

    // Book appointment
    public void bookAppointment() {
        waitUntilClickable(btnBookAppointment).click();
    }

    // Radio buttons
    public void selectMedicare() {
        waitUntilClickable(radioMedicare).click();
    }
    public void selectMedicaid() {
        waitUntilClickable(radioMedicaid).click();
    }
    public void selectNone() {
        waitUntilClickable(radioNone).click();
    }

    public boolean isMedicareSelected() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(radioMedicare)).isSelected();
    }
    public boolean isMedicaidSelected() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(radioMedicaid)).isSelected();
    }
    public boolean isNoneSelected() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(radioNone)).isSelected();
    }
}