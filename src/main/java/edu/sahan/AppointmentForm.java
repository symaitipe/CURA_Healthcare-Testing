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
    private static final By datepicker = By.className("datepicker");

    // Helper: Wait until element is clickable
    private WebElement waitUntilClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    // Helper: Wait for datepicker to be invisible
    private void waitForDatepickerToClose() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(datepicker));
        } catch (Exception e) {
            // Datepicker not present or already closed
        }
    }

    // Helper: Click using JavaScript as fallback
    private void jsClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    // Facility
    public void selectFacility(String facility) {
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(facilityDropdown));
        new Select(dropdown).selectByVisibleText(facility);
    }

    // Hospital readmission checkbox
    public void setHospitalReadmission(boolean apply) {
        waitForDatepickerToClose(); // Ensure datepicker is closed
        WebElement checkbox = waitUntilClickable(chkHospitalReadmission);
        if (checkbox.isSelected() != apply) {
            try {
                checkbox.click();
            } catch (ElementClickInterceptedException e) {
                System.out.println("Checkbox click intercepted, using JavaScript click.");
                jsClick(checkbox);
            }
        }
    }

    // Visit date
    public void setVisitDate(String date) {
        WebElement dateInput = waitUntilClickable(txtVisitDate);
        dateInput.clear();
        dateInput.sendKeys(date);
        // Press Enter to close datepicker
        dateInput.sendKeys(Keys.ENTER);
        waitForDatepickerToClose();
    }

    // Comment
    public void setComment(String comment) {
        WebElement commentBox = waitUntilClickable(txtComment);
        commentBox.clear();
        commentBox.sendKeys(comment);
    }

    // Book appointment
    public void bookAppointment() {
        WebElement button = waitUntilClickable(btnBookAppointment);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", button);
        try {
            button.click();
        } catch (ElementClickInterceptedException e) {
            System.out.println("Book button click intercepted, using JavaScript click.");
            jsClick(button);
        }
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