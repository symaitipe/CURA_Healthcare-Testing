package edu.sahan;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AppointmentFormTest extends BaseTest {

    @ParameterizedTest
    @CsvSource({
            "Tokyo CURA Healthcare Center, Medicare, Yes, 20/12/2025, Comment 1",
            "Tokyo CURA Healthcare Center, Medicaid, No, 21/12/2025, Comment 2",
            "Tokyo CURA Healthcare Center, None, Yes, 22/12/2025, Comment 3",
            "Hongkong CURA Healthcare Center, Medicare, No, 23/12/2025, Comment 4",
            "Hongkong CURA Healthcare Center, Medicaid, Yes, 24/12/2025, Comment 5",
            "Hongkong CURA Healthcare Center, None, No, 25/12/2025, Comment 6",
            "Seoul CURA Healthcare Center, Medicare, Yes, 26/12/2025, Comment 7",
            "Seoul CURA Healthcare Center, Medicaid, No, 27/12/2025, Comment 8",
            "Seoul CURA Healthcare Center, None, Yes, 28/12/2025, Comment 9"
    })
    void testBookingWithVariousCombinations_AndVerifyHistory(String facility, String program, String readmission, String date, String comment) {
        homePage.loginIfNotLoggedIn();
        AppointmentForm form = homePage.goToMakeAppointmentPage();

        form.selectFacility(facility);
        form.setHospitalReadmission(readmission.equals("Yes"));

        switch (program) {
            case "Medicare" -> form.selectMedicare();
            case "Medicaid" -> form.selectMedicaid();
            case "None" -> form.selectNone();
        }

        form.setVisitDate(date);
        form.setComment(comment);
        form.bookAppointment();

        AppointmentConfirmationPage confirmation = new AppointmentConfirmationPage(driver);
        assertTrue(confirmation.isAppointmentConfirmed(),
                "Appointment should be confirmed for: " + facility + " with " + program);

        HistoryPage history = confirmation.goToHistoryPage();
        List<WebElement> appointments = history.findAllAppointmentsByDate(date);
        assertFalse(appointments.isEmpty(), "Appointment should exist in history for date: " + date);

        WebElement appointment = appointments.get(0);

        assertEquals(facility, history.getFacility(appointment), "Facility mismatch in history");
        assertEquals(readmission, history.getReadmission(appointment), "Readmission mismatch in history");
        assertEquals(program, history.getProgram(appointment), "Program mismatch in history");
        assertEquals(comment, history.getComment(appointment), "Comment mismatch in history");
    }


    @Test
    void testBookAppointmentWithMaxLengthComment() {
        homePage.loginIfNotLoggedIn();
        AppointmentForm form = homePage.goToMakeAppointmentPage();
        form.selectFacility("Tokyo CURA Healthcare Center");
        form.setHospitalReadmission(false);
        form.selectMedicaid();
        form.setVisitDate("25/12/2025");
        String longComment = "A".repeat(500);
        form.setComment(longComment);
        form.bookAppointment();

        AppointmentConfirmationPage confirmation = new AppointmentConfirmationPage(driver);
        assertTrue(confirmation.isAppointmentConfirmed(),
                "Appointment should be confirmed with max-length comment");
    }

    @Test
    void testFacilityDropdownOptions() {
        homePage.loginIfNotLoggedIn();
        AppointmentForm form = homePage.goToMakeAppointmentPage();
        List<String> actualOptions = form.getAllFacilityOptions();
        List<String> expectedOptions = List.of(
                "Tokyo CURA Healthcare Center",
                "Hongkong CURA Healthcare Center",
                "Seoul CURA Healthcare Center"
        );
        assertEquals(expectedOptions, actualOptions,
                "Facility dropdown options should match expected list");
    }

    @Test
    void testBookingWithoutLoginShouldFail() {
        AppointmentForm form = homePage.goToMakeAppointmentPage();
        assertTrue(form.isRedirectedToLogin(),
                "User should be redirected to login if not authenticated");
    }

    @Test
    void testBookAppointmentWithInvalidDateFormat() {
        homePage.loginIfNotLoggedIn();
        AppointmentForm form = homePage.goToMakeAppointmentPage();
        form.selectFacility("Hongkong CURA Healthcare Center");
        form.setHospitalReadmission(true);
        form.selectMedicare();
        form.setVisitDate("2026-03-03");
        form.setComment("Invalid date format");
        String beforeUrl = driver.getCurrentUrl();
        form.bookAppointment();
        assertEquals(beforeUrl, driver.getCurrentUrl(), "Invalid date format should not navigate away");
    }

    @Test
    void testBookAppointmentWithoutDate() {
        homePage.loginIfNotLoggedIn();
        AppointmentForm form = homePage.goToMakeAppointmentPage();
        form.selectFacility("Seoul CURA Healthcare Center");
        form.setHospitalReadmission(true);
        form.selectMedicaid();
        form.setComment("No date");
        String beforeUrl = driver.getCurrentUrl();
        form.bookAppointment();
        assertEquals(beforeUrl, driver.getCurrentUrl(), "Booking without date should not navigate away");
    }

    @Test
    void testBookAppointmentWithOldDate() {
        homePage.loginIfNotLoggedIn();
        AppointmentForm form = homePage.goToMakeAppointmentPage();
        form.selectFacility("Hongkong CURA Healthcare Center");
        form.setHospitalReadmission(true);
        form.selectMedicare();
        form.setVisitDate("01/01/2025");
        form.setComment("Old date");
        String beforeUrl = driver.getCurrentUrl();
        form.bookAppointment();
        assertEquals(beforeUrl, driver.getCurrentUrl(), "Booking with old date should not navigate away");
    }




}
