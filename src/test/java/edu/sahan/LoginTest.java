package edu.sahan;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class LoginTest extends BaseTest{

    private final String VALID_USERNAME = "John Doe";
    private final String VALID_PASSWORD = "ThisIsNotAPassword";
    private final String INVALID_USERNAME = "wrongUser";
    private final String INVALID_PASSWORD = "wrongPass";

    LoginResultPage resultPage;

    @Test
    public void testSuccessfulLogin() {
        BurgerMenu burgerMenu = homePage.goToBurgerMenu();
        LoginForm loginForm = burgerMenu.goToLoginPage();
        resultPage = loginForm.loginWithValidCredentials(VALID_USERNAME, VALID_PASSWORD);

        assertTrue(resultPage.isLoginSuccessful(), "Login was not successful");

        homePage.goToBurgerMenu().LogOut();
    }

    @Test
    public void testInvalidUsername() {
        BurgerMenu burgerMenu = homePage.goToBurgerMenu();
        LoginForm loginForm = burgerMenu.goToLoginPage();
         resultPage = loginForm.loginWithInvalidCredentials(INVALID_USERNAME, VALID_PASSWORD);

        assertFalse(resultPage.isLoginSuccessful(), "Login was successful");

    }

    @Test
    public void testInvalidPassword() {
        BurgerMenu burgerMenu = homePage.goToBurgerMenu();
        LoginForm loginForm = burgerMenu.goToLoginPage();
        resultPage = loginForm.loginWithInvalidCredentials(VALID_USERNAME, INVALID_PASSWORD);

        assertFalse(resultPage.isLoginSuccessful(), "Login was successful");
    }

    @Test
    public void testEmptyCredentials() {
        BurgerMenu burgerMenu = homePage.goToBurgerMenu();
        LoginForm loginForm = burgerMenu.goToLoginPage();
        resultPage = loginForm.loginWithEmptyCredentials();

        assertFalse(resultPage.isLoginSuccessful(), "Login was successful");
    }

}
