package edu.sahan;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Objects;

class AppointmentFormTest extends BaseTest {

    @Test
    void testBookingFailsIfNotLoggedIn() {
        if (homePage.isUserLoggedIn()) {
            homePage.goToBurgerMenu().logOut();
        }
        homePage.goToMakeAppointmentPage();
        String url = driver.getCurrentUrl();
        assertNotNull(url);
        assertTrue(url.contains("profile.php#login") || Objects.requireNonNull(driver.getPageSource()).contains("Login"),
                "User should be redirected to login page if not logged in");
    }

    @Test
    void testBookAppointmentWithMedicare() {
        if (!homePage.isUserLoggedIn()) {
            homePage.goToBurgerMenu().goToLoginPage().loginWithValidCredentials("John Doe", "ThisIsNotAPassword");
        }
        AppointmentForm form = homePage.goToMakeAppointmentPage();
        form.selectFacility("Hongkong CURA Healthcare Center");
        form.setHospitalReadmission(true);
        form.selectMedicare();
        form.setVisitDate("12/12/2025");
        form.setComment("Medicare test");
        form.bookAppointment();

        AppointmentConfirmationPage confirmation = new AppointmentConfirmationPage(driver);
        assertTrue(confirmation.isAppointmentConfirmed());
    }

    // Select Medicaid for Health program
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
    }

    //select None for Health program
    @Test
    void testBookAppointmentWithNoneSelected() {
        if (!homePage.isUserLoggedIn()) {
            homePage.goToBurgerMenu().goToLoginPage().loginWithValidCredentials("John Doe", "ThisIsNotAPassword");
        }
        AppointmentForm form = homePage.goToMakeAppointmentPage();
        form.selectFacility("Seoul CURA Healthcare Center");
        form.setHospitalReadmission(true);
        form.selectNone();
        form.setVisitDate("01/01/2025");
        form.setComment("No program selected");
        form.bookAppointment();

        AppointmentConfirmationPage confirmation = new AppointmentConfirmationPage(driver);
        assertTrue(confirmation.isAppointmentConfirmed());
    }

    // Test for Medicaid without readmission
    @Test
    void testBookAppointmentWithMedicaidNoReadmission() {
        if (!homePage.isUserLoggedIn()) {
            homePage.goToBurgerMenu().goToLoginPage().loginWithValidCredentials("John Doe", "ThisIsNotAPassword");
        }
        AppointmentForm form = homePage.goToMakeAppointmentPage();
        form.selectFacility("Tokyo CURA Healthcare Center");
        form.setHospitalReadmission(false);
        form.selectMedicaid();
        form.setVisitDate("02/02/2025");
        form.setComment("Medicaid without readmission");
        form.bookAppointment();

        AppointmentConfirmationPage confirmation = new AppointmentConfirmationPage(driver);
        assertTrue(confirmation.isAppointmentConfirmed());
    }


    //Empty comment test
    @Test
    void testBookAppointmentWithEmptyComment() {
        if (!homePage.isUserLoggedIn()) {
            homePage.goToBurgerMenu().goToLoginPage().loginWithValidCredentials("John Doe", "ThisIsNotAPassword");
        }
        AppointmentForm form = homePage.goToMakeAppointmentPage();
        form.selectFacility("Seoul CURA Healthcare Center");
        form.setHospitalReadmission(true);
        form.selectMedicare();
        form.setVisitDate("03/03/2025");
        //=====================Empty comment
        form.bookAppointment();

        AppointmentConfirmationPage confirmation = new AppointmentConfirmationPage(driver);
        assertTrue(confirmation.isAppointmentConfirmed());
    }

    // Test for booking with invalid date format -- Assuming the system requires a specific date format (MM/DD/YYYY)
    @Test
    void testBookAppointmentWithInvalidDateFormat() {
        if (!homePage.isUserLoggedIn()) {
            homePage.goToBurgerMenu().goToLoginPage().loginWithValidCredentials("John Doe", "ThisIsNotAPassword");
        }
        AppointmentForm form = homePage.goToMakeAppointmentPage();
        form.selectFacility("Hongkong CURA Healthcare Center");
        form.setHospitalReadmission(true);
        form.selectMedicare();
        form.setVisitDate("2025-03-03"); //===================== Wrong format
        form.setComment("Invalid date format");
        String beforeUrl = driver.getCurrentUrl();
        form.bookAppointment();
        assertEquals(beforeUrl, driver.getCurrentUrl());
    }

    // Test for booking without visit date -- Assuming the system does not allow booking without a date
    @Test
    void testBookAppointmentWithoutDate() {
        if (!homePage.isUserLoggedIn()) {
            homePage.goToBurgerMenu().goToLoginPage().loginWithValidCredentials("John Doe", "ThisIsNotAPassword");
        }
        AppointmentForm form = homePage.goToMakeAppointmentPage();
        form.selectFacility("Seoul CURA Healthcare Center");
        form.setHospitalReadmission(true);
        form.selectMedicaid();
        //===================== Not setting visit date
        form.setComment("No date");
        String beforeUrl = driver.getCurrentUrl();
        form.bookAppointment();
        assertEquals(beforeUrl, driver.getCurrentUrl());
    }

    // Test for booking with old date ---- Assuming the system does not allow booking for past dates
    @Test
    void testBookAppointmentWithOldDate() {
        if (!homePage.isUserLoggedIn()) {
            homePage.goToBurgerMenu().goToLoginPage().loginWithValidCredentials("John Doe", "ThisIsNotAPassword");
        }
        AppointmentForm form = homePage.goToMakeAppointmentPage();
        form.selectFacility("Hongkong CURA Healthcare Center");
        form.setHospitalReadmission(true);
        form.selectMedicare();
        form.setVisitDate("2025-03-03"); //===================== old date
        form.setComment("Old date");
        String beforeUrl = driver.getCurrentUrl();
        form.bookAppointment();
        assertEquals(beforeUrl, driver.getCurrentUrl());
    }

}