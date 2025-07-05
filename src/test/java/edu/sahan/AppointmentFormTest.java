package edu.sahan;

import org.junit.jupiter.api.Test;


import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;



public class AppointmentFormTest extends BaseTest {


    @Test
    void testBookAppointmentWithMedicaid() {
        if (!homePage.isUserLoggedIn()) {
            homePage.goToBurgerMenu().goToLoginPage().loginWithValidCredentials("John Doe", "ThisIsNotAPassword");
        }
        AppointmentForm form = homePage.goToMakeAppointmentPage();
        form.selectFacility("Hongkong CURA Healthcare Center");
        form.setHospitalReadmission(true);
        form.selectMedicaid();
        form.setVisitDate("12/12/2025");
        form.setComment("Medicaid test");
        form.bookAppointment();

        AppointmentConfirmationPage confirmation = new AppointmentConfirmationPage(driver);
        assertTrue(confirmation.isAppointmentConfirmed());

        HistoryPage history = confirmation.goToHistoryPage();
        List<WebElement> appointments = history.findAllAppointmentsByDate("12/12/2025");
        assertFalse(appointments.isEmpty());
        WebElement appointment = appointments.get(0);
        assertEquals(history.getFacility(appointment), "Hongkong CURA Healthcare Center");
        assertEquals(history.getReadmission(appointment), "Yes");
        assertEquals(history.getProgram(appointment), "Medicaid");
        assertEquals(history.getComment(appointment), "Medicaid test");
    }

    @Test
    void testBookAppointmentWithNoneSelected() {
        if (!homePage.isUserLoggedIn()) {
            homePage.goToBurgerMenu().goToLoginPage().loginWithValidCredentials("John Doe", "ThisIsNotAPassword");
        }
        AppointmentForm form = homePage.goToMakeAppointmentPage();
        form.selectFacility("Seoul CURA Healthcare Center");
        form.setHospitalReadmission(true);
        form.selectNone();
        form.setVisitDate("01/01/2026");
        form.setComment("No program selected");
        form.bookAppointment();

        AppointmentConfirmationPage confirmation = new AppointmentConfirmationPage(driver);
        assertTrue(confirmation.isAppointmentConfirmed());

        HistoryPage history = confirmation.goToHistoryPage();
        List<WebElement> appointments = history.findAllAppointmentsByDate("01/01/2026");
        assertFalse(appointments.isEmpty());
        WebElement appointment = appointments.get(0);
        assertEquals(history.getFacility(appointment), "Seoul CURA Healthcare Center");
        assertEquals(history.getReadmission(appointment), "Yes");
        assertEquals(history.getProgram(appointment), "None");
        assertEquals(history.getComment(appointment), "No program selected");
    }

    @Test
    void testBookAppointmentWithMedicaidNoReadmission() {
        if (!homePage.isUserLoggedIn()) {
            homePage.goToBurgerMenu().goToLoginPage().loginWithValidCredentials("John Doe", "ThisIsNotAPassword");
        }
        AppointmentForm form = homePage.goToMakeAppointmentPage();
        form.selectFacility("Tokyo CURA Healthcare Center");
        form.setHospitalReadmission(false);
        form.selectMedicaid();
        form.setVisitDate("02/02/2026");
        form.setComment("Medicaid without readmission");
        form.bookAppointment();

        AppointmentConfirmationPage confirmation = new AppointmentConfirmationPage(driver);
        assertTrue(confirmation.isAppointmentConfirmed());

        HistoryPage history = confirmation.goToHistoryPage();
        List<WebElement> appointments = history.findAllAppointmentsByDate("02/02/2026");
        assertFalse(appointments.isEmpty());
        WebElement appointment = appointments.get(0);
        assertEquals(history.getFacility(appointment), "Tokyo CURA Healthcare Center");
        assertEquals(history.getReadmission(appointment), "No");
        assertEquals(history.getProgram(appointment), "Medicaid");
        assertEquals(history.getComment(appointment), "Medicaid without readmission");
    }

    @Test
    void testBookAppointmentWithEmptyComment() {
        if (!homePage.isUserLoggedIn()) {
            homePage.goToBurgerMenu().goToLoginPage().loginWithValidCredentials("John Doe", "ThisIsNotAPassword");
        }
        AppointmentForm form = homePage.goToMakeAppointmentPage();
        form.selectFacility("Seoul CURA Healthcare Center");
        form.setHospitalReadmission(true);
        form.selectMedicare();
        form.setVisitDate("03/03/2026");
        form.bookAppointment();

        AppointmentConfirmationPage confirmation = new AppointmentConfirmationPage(driver);
        assertTrue(confirmation.isAppointmentConfirmed());

        HistoryPage history = confirmation.goToHistoryPage();
        List<WebElement> appointments = history.findAllAppointmentsByDate("03/03/2026");
        assertFalse(appointments.isEmpty());
        WebElement appointment = appointments.get(0);
        assertEquals(history.getFacility(appointment), "Seoul CURA Healthcare Center");
        assertEquals(history.getReadmission(appointment), "Yes");
        assertEquals(history.getProgram(appointment), "Medicare");
        assertEquals(history.getComment(appointment), "");
    }

    @Test
    void testBookAppointmentWithInvalidDateFormat() {
        if (!homePage.isUserLoggedIn()) {
            homePage.goToBurgerMenu().goToLoginPage().loginWithValidCredentials("John Doe", "ThisIsNotAPassword");
        }
        AppointmentForm form = homePage.goToMakeAppointmentPage();
        form.selectFacility("Hongkong CURA Healthcare Center");
        form.setHospitalReadmission(true);
        form.selectMedicare();
        form.setVisitDate("2026-03-03");
        form.setComment("Invalid date format");
        String beforeUrl = driver.getCurrentUrl();
        form.bookAppointment();
        assertEquals(beforeUrl, driver.getCurrentUrl());
    }

    @Test
    void testBookAppointmentWithoutDate() {
        if (!homePage.isUserLoggedIn()) {
            homePage.goToBurgerMenu().goToLoginPage().loginWithValidCredentials("John Doe", "ThisIsNotAPassword");
        }
        AppointmentForm form = homePage.goToMakeAppointmentPage();
        form.selectFacility("Seoul CURA Healthcare Center");
        form.setHospitalReadmission(true);
        form.selectMedicaid();
        form.setComment("No date");
        String beforeUrl = driver.getCurrentUrl();
        form.bookAppointment();
        assertEquals(beforeUrl, driver.getCurrentUrl());
    }

    @Test
    void testBookAppointmentWithOldDate() {
        if (!homePage.isUserLoggedIn()) {
            homePage.goToBurgerMenu().goToLoginPage().loginWithValidCredentials("John Doe", "ThisIsNotAPassword");
        }
        AppointmentForm form = homePage.goToMakeAppointmentPage();
        form.selectFacility("Hongkong CURA Healthcare Center");
        form.setHospitalReadmission(true);
        form.selectMedicare();
        form.setVisitDate("01/01/2025"); // Past date relative to current date
        form.setComment("Old date");
        String beforeUrl = driver.getCurrentUrl();
        form.bookAppointment();
        assertEquals(beforeUrl, driver.getCurrentUrl());
    }

    @Test
    void testBookAppointmentWithMedicaidAndNewFacility() {
        if (!homePage.isUserLoggedIn()) {
            homePage.goToBurgerMenu().goToLoginPage().loginWithValidCredentials("John Doe", "ThisIsNotAPassword");
        }
        AppointmentForm form = homePage.goToMakeAppointmentPage();
        form.selectFacility("Seoul CURA Healthcare Center");
        form.setHospitalReadmission(false);
        form.selectMedicaid();
        form.setVisitDate("15/08/2025");
        form.setComment("New facility test");
        form.bookAppointment();

        AppointmentConfirmationPage confirmation = new AppointmentConfirmationPage(driver);
        assertTrue(confirmation.isAppointmentConfirmed());

        HistoryPage history = confirmation.goToHistoryPage();
        List<WebElement> appointments = history.findAllAppointmentsByDate("15/08/2025");
        assertFalse(appointments.isEmpty());
        WebElement appointment = appointments.get(0);
        assertEquals(history.getFacility(appointment), "Seoul CURA Healthcare Center");
        assertEquals(history.getReadmission(appointment), "No");
        assertEquals(history.getProgram(appointment), "Medicaid");
        assertEquals(history.getComment(appointment), "New facility test");
    }
}