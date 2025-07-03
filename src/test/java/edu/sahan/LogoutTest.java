// src/test/java/edu/sahan/LogoutTest.java
package edu.sahan;

import org.junit.jupiter.api.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

public class LogoutTest extends BaseTest {
    private static final Duration TIMEOUT = Duration.ofSeconds(5);
    private static final String HOME_URL = "https://katalon-demo-cura.herokuapp.com/";
    private static final String VALID_USERNAME = "John Doe";
    private static final String VALID_PASSWORD = "ThisIsNotAPassword";

    @Test
    @DisplayName("Test logout when user is logged in")
    public void testLogout() {
        // 1. Login first
        LoginResultPage resultPage = homePage.goToBurgerMenu()
                .goToLoginPage()
                .loginWithValidCredentials(VALID_USERNAME, VALID_PASSWORD);

        // Verify successful login
        assertTrue(resultPage.isLoginSuccessful(), "Should be on appointment page after login");
        assertTrue(homePage.isUserLoggedIn(), "User should be logged in");

        // 2. Perform logout
        homePage.goToBurgerMenu().logOut();

        // 3. Verify logout success
        verifySuccessfulLogout();
    }

    private void verifySuccessfulLogout() {
        new WebDriverWait(driver, TIMEOUT)
                .until(ExpectedConditions.urlToBe(HOME_URL));

        assertFalse(homePage.isUserLoggedIn(), "User should be logged out");
        assertTrue(homePage.isLoginMenuItemVisible(), "Login option should be visible");
        assertTrue(homePage.isBurgerMenuCollapsed(), "Burger menu should collapse after logout");
    }
}