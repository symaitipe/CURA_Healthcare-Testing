
package edu.sahan;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


public class LogoutTest extends BaseTest {
    // Failed Booking if not logged in
    @Test
    void testBookingFailsIfNotLoggedIn() {
        if (homePage.isUserLoggedIn()) {
            BurgerMenu menu = homePage.goToBurgerMenu();
            menu.logOut();
        }
        homePage.goToMakeAppointmentPage();
        String url = driver.getCurrentUrl();
        assertNotNull(url);
        assertTrue(url.contains("profile.php#login") || driver.getPageSource().contains("Login"),
                "User should be redirected to login page if not logged in");
    }

    // Health Program - Select Medicare
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
        assertTrue(driver.getCurrentUrl().contains("appointment.php#summary"),
                "Should be redirected to appointment confirmation page");
    }

    // Health Program - Select Medicaid
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
        assertTrue(driver.getCurrentUrl().contains("appointment.php#summary"),
                "Should be redirected to appointment confirmation page");
    }

    // Health Program - Select None
    @Test
    void testBookAppointmentWithNoneSelected() {
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
        assertTrue(driver.getCurrentUrl().contains("appointment.php#summary"),
                "Should be redirected to appointment confirmation page");
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
        form.setVisitDate("02/02/2025");
        form.setComment("Medicaid without readmission");
        form.bookAppointment();
        assertTrue(driver.getCurrentUrl().contains("appointment.php#summary"),
                "Should be redirected to appointment confirmation page");
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
        form.setVisitDate("03/03/2025");
        form.setComment("");
        form.bookAppointment();
        assertTrue(driver.getCurrentUrl().contains("appointment.php#summary"),
                "Should be redirected to appointment confirmation page");
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
        form.setVisitDate("03-03-2025"); // Wrong format
        form.setComment("Invalid date format");
        String beforeUrl = driver.getCurrentUrl();
        form.bookAppointment();
        assertEquals(beforeUrl, driver.getCurrentUrl(), "Should stay on form page due to invalid date");
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
        form.setVisitDate(""); // No date entered
        form.setComment("No date");
        String beforeUrl = driver.getCurrentUrl();
        form.bookAppointment();
        assertEquals(beforeUrl, driver.getCurrentUrl(), "Should stay on form page due to missing date");
    }

    @Test
    void testBookAppointmentWithoutSelectingFacility() {
        if (!homePage.isUserLoggedIn()) {
            homePage.goToBurgerMenu().goToLoginPage().loginWithValidCredentials("John Doe", "ThisIsNotAPassword");
        }
        AppointmentForm form = homePage.goToMakeAppointmentPage();
        form.setHospitalReadmission(true);
        form.selectMedicare();
        form.setVisitDate("04/04/2025");
        form.setComment("No facility selected");
        String beforeUrl = driver.getCurrentUrl();
        form.bookAppointment();
        assertEquals(beforeUrl, driver.getCurrentUrl(), "Should stay on form page due to missing facility");
    }
}