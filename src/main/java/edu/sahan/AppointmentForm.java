package edu.sahan;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class AppointmentForm {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private static final By facilityDropdown = By.id("combo_facility");
    private static final By chkHospitalReadmission = By.id("chk_hospotal_readmission");
    private static final By txtVisitDate = By.id("txt_visit_date");
    private static final By txtComment = By.id("txt_comment");
    private static final By btnBookAppointment = By.id("btn-book-appointment");

    private static final By radioMedicare = By.id("radio_program_medicare");
    private static final By radioMedicaid = By.id("radio_program_medicaid");
    private static final By radioNone = By.id("radio_program_none");



    public AppointmentForm(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void selectFacility(String facility) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(facilityDropdown));
        Select select = new Select(dropdown);
        select.selectByVisibleText(facility);
    }

    public void setHospitalReadmission(boolean apply) {
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(chkHospitalReadmission));
        if (checkbox.isSelected() != apply) {
            checkbox.click();
        }
    }

    public void selectMedicare() {
        wait.until(ExpectedConditions.elementToBeClickable(radioMedicare)).click();
    }

    public void selectMedicaid() {
        wait.until(ExpectedConditions.elementToBeClickable(radioMedicaid)).click();
    }

    public void selectNone() {
        wait.until(ExpectedConditions.elementToBeClickable(radioNone)).click();
    }

    public void setVisitDate(String date) {
        wait.until(ExpectedConditions.elementToBeClickable(txtVisitDate)).sendKeys(date);
        driver.findElement(txtVisitDate).sendKeys(Keys.TAB);
    }

    public void setComment(String comment) {
        WebElement commentBox = wait.until(ExpectedConditions.elementToBeClickable(txtComment));
        commentBox.clear();
        commentBox.sendKeys(comment);
    }

    public void bookAppointment() {
        wait.until(ExpectedConditions.elementToBeClickable(btnBookAppointment)).click();
    }

    public List<String> getAllFacilityOptions() {
             Select allFacilitiesElements = new Select(wait.until(ExpectedConditions.elementToBeClickable(facilityDropdown)));
             return allFacilitiesElements.getOptions().stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public boolean isRedirectedToLogin() {
        try {
            return wait.until(ExpectedConditions.urlContains("login"));
        } catch (TimeoutException e) {
            return false;
        }
    }
}