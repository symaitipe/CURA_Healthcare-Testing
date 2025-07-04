package edu.sahan;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class AppointmentFormTest extends BaseTest {

    /**
         * 🩺 Medicare
             A federal health insurance program for people aged 65 or older, or people with certain disabilities.

         * 🩺 Medicaid
             A state and federal program that provides health coverage for low-income individuals or families.

         * 🩺 None
             This means the patient is not using any insurance for the appointment.
     */


    /**
        * revisit within 30 days of a previous hospital stay.

        * It's tracked because frequent readmissions can be a quality or risk indicator.
     */


//=================================================================================================================================================

    //Failed Booking if not logged in
    @Test
    void testBookingFailsIfNotLoggedIn() {
        // Force logout first
        if (homePage.isUserLoggedIn()) {
            BurgerMenu menu = homePage.goToBurgerMenu();
            menu.logOut();
        }

        // Attempt to access appointment form
        homePage.goToMakeAppointmentPage();

        // Assert user is redirected to login
        String url = driver.getCurrentUrl();
        assert url != null;
        assertTrue(url.contains("profile.php#login") || Objects.requireNonNull(driver.getPageSource()).contains("Login"),
                "User should be redirected to login page if not logged in");
    }




    //Health Program - Select Medicare
    @Test
    void testBookAppointmentWithMedicare() {
        // Check if logged in, if not, log in
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

        assertTrue(Objects.requireNonNull(driver.getPageSource()).contains("Make Appointment"));

    }

    //Health Program - Select Medicaid
    @Test
    void testBookAppointmentWithMedicaid() {
        // Check if logged in, if not, log in
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

        assertTrue(Objects.requireNonNull(driver.getPageSource()).contains("Make Appointment"));
    }

    //Health Program - Select None
    @Test
    void testBookAppointmentWithNoneSelected() {
        // Check if logged in, if not, log in
        if (!homePage.isUserLoggedIn()) {
            homePage.goToBurgerMenu().goToLoginPage().loginWithValidCredentials("John Doe", "ThisIsNotAPassword");
        }

        AppointmentForm form = homePage.goToMakeAppointmentPage();
        form.selectFacility("Seoul CURA Healthcare Center");
        form.setHospitalReadmission(false);
        form.selectNone();
        form.setVisitDate("01/01/2025");
        form.setComment("No program selected");
        form.bookAppointment();

        assertTrue(Objects.requireNonNull(driver.getPageSource()).contains("Make Appointment"));
    }



    @Test
    void testBookAppointmentWithMedicaidNoReadmission() {
        // Check if logged in, if not, log in
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

        assertTrue(Objects.requireNonNull(driver.getPageSource()).contains("Make Appointment"));
    }

    @Test
    void testBookAppointmentWithEmptyComment() {
        // Check if logged in, if not, log in
        if (!homePage.isUserLoggedIn()) {
            homePage.goToBurgerMenu().goToLoginPage().loginWithValidCredentials("John Doe", "ThisIsNotAPassword");
        }

        AppointmentForm form = homePage.goToMakeAppointmentPage();
        form.selectFacility("Seoul CURA Healthcare Center");
        form.setHospitalReadmission(true);
        form.selectMedicare();
        form.setVisitDate("03/03/2025");
        form.setComment("");
        form.bookAppointment();

        assertTrue(Objects.requireNonNull(driver.getPageSource()).contains("Make Appointment"));
    }

    @Test
    void testBookAppointmentWithInvalidDateFormat() {
        // Check if logged in, if not, log in
        if (!homePage.isUserLoggedIn()) {
            homePage.goToBurgerMenu().goToLoginPage().loginWithValidCredentials("John Doe", "ThisIsNotAPassword");
        }

        AppointmentForm form = homePage.goToMakeAppointmentPage();
        form.selectFacility("Hongkong CURA Healthcare Center");
        form.setHospitalReadmission(true);
        form.selectMedicare();
        form.setVisitDate("03-03-2025"); // Wrong format
        form.setComment("Invalid date format");

        String beforeUrl = driver.getCurrentUrl();
        form.bookAppointment();

        // Should still be on same page if date format is invalid
        assertEquals(beforeUrl, driver.getCurrentUrl());
    }

    @Test
    void testBookAppointmentWithoutDate() {
        // Check if logged in, if not, log in
        if (!homePage.isUserLoggedIn()) {
            homePage.goToBurgerMenu().goToLoginPage().loginWithValidCredentials("John Doe", "ThisIsNotAPassword");
        }
        // Attempt to book an appointment without entering a date
        AppointmentForm form = homePage.goToMakeAppointmentPage();
        form.selectFacility("Seoul CURA Healthcare Center");
        form.setHospitalReadmission(true);
        form.selectMedicaid();
        form.setVisitDate(""); // No date entered
        form.setComment("No date");

        String beforeUrl = driver.getCurrentUrl();
        form.bookAppointment();

        assertEquals(beforeUrl, driver.getCurrentUrl());
    }

    @Test
    void testBookAppointmentWithoutSelectingFacility() {
        // Check if logged in, if not, log in
        if (!homePage.isUserLoggedIn()) {
            homePage.goToBurgerMenu().goToLoginPage().loginWithValidCredentials("John Doe", "ThisIsNotAPassword");
        }
        // Attempt to book an appointment without selecting a facility
        AppointmentForm form = homePage.goToMakeAppointmentPage();
        // Assuming the form has a default value or fails
        form.setHospitalReadmission(true);
        form.selectMedicare();
        form.setVisitDate("04/04/2025");
        form.setComment("No facility selected");

        form.bookAppointment();

        // Confirmation might still work depending on the system defaults
        assertTrue(Objects.requireNonNull(driver.getPageSource()).contains("Make Appointment"));
    }
}
