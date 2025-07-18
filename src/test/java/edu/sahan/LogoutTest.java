
package edu.sahan;
import org.junit.jupiter.api.*;

import java.util.Objects;

import static  org.junit.jupiter.api.Assertions.*;


public class LogoutTest extends BaseTest {

    @Test
    void logout() {
        homePage.loginIfNotLoggedIn();
        homePage.goToBurgerMenu().logOut();
        assertTrue(homePage.isBurgerMenuCollapsed(), "User has not successfully logged Out");

    }

}