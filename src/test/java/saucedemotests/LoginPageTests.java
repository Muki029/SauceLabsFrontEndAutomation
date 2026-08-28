package saucedemotests;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.LoginPage;
import pages.ProductsPage;

import static org.junit.Assert.*;

public class LoginPageTests {

    private WebDriver driver;
    private LoginPage loginPage;
    private ProductsPage productpage;


    @BeforeEach
    public void setUp() {
        //initalize chrome driver
        ChromeOptions options = new ChromeOptions(); if (System.getenv("JENKINS_HOME") != null) { options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage"); } driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");

        loginPage = new LoginPage(driver);
        productpage = new ProductsPage(driver);
    }

    @Test

    public void successfulLoginTest() {
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");

        loginPage.clickLogin();
        loginPage.pressSpacebar();

        assertEquals("Products", productpage.getPageTitle());
        //user is redirected to the inventory page after successful login -
        assertEquals("https://www.saucedemo.com/inventory.html", driver.getCurrentUrl());

    }

    @Test
    public void unsuccessfulLoginFalsePasswordTest() {
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("12341234");
        loginPage.clickLogin();

        assertEquals("Epic sadface: Username and password do not match any user in this service", loginPage.GetErrorMessage());

    }

    @Test
    public void unsuccessfulLoginFalseUsernameTest() {
        loginPage.enterUsername("standardd_usesr");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        assertEquals("Epic sadface: Username and password do not match any user in this service", loginPage.GetErrorMessage());
    }

    @Test
    public void unsuccessfulLoginUsernamewithoutPasswordTest() {
        loginPage.enterUsername("standard_user");

        loginPage.clickLogin();

        assertEquals("Epic sadface: Password is required", loginPage.GetErrorMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"standard_user", "invalidUser123"})
    public void unsuccessfulLoginUsernamewithoutPasswordTestParameterized(String username) {

        loginPage.enterUsername(username);

        loginPage.clickLogin();

        assertEquals("Epic sadface: Password is required", loginPage.GetErrorMessage());

    }

    @ParameterizedTest
    @ValueSource(strings = {"standard_user", "locked_out_user", "problem_user", "performance_glitch_user", "error_user", "visual_user"})

    public void loginTest(String username) {

        loginPage.enterUsername(username);
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        if (username.equals("locked_out_user")) {
            String errorText = loginPage.GetErrorMessage();
            assertEquals("Epic sadface: Sorry, this user has been locked out.", errorText);
            System.out.println("locked_out_user failed to log in ;");
            assertEquals("https://www.saucedemo.com/", driver.getCurrentUrl());
        } else {

            assertEquals("https://www.saucedemo.com/inventory.html", driver.getCurrentUrl());


        }
    }

    @Test
    public void unsuccessfulLoginPasswordWithoutUsernameTest() {

        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        assertEquals("Epic sadface: Username is required", loginPage.GetErrorMessage());
    }

    @Test
    public void unsuccessfulLogiEmptyUsernameEmptyPasswordTest() {

        loginPage.clickLogin();

        assertEquals("Epic sadface: Username is required", loginPage.GetErrorMessage());
    }

    @Test
    public void isErrorMessageDisplayedTest() {
        loginPage.clickLogin();
        assertTrue(loginPage.isErrorMessageDisplayed());
    }

    @Test
    public void isErrorMessageDisplayedClickXTest() {
        loginPage.clickLogin();
        loginPage.clickErrorMessageXButton();

        assertFalse(loginPage.isErrorMessageDisplayed());

    }

    @Test
    public void loginFormInitialStateUITest() {
        assertEquals("\"DM Sans\", Arial, Helvetica, sans-serif", loginPage.getUserNameFieldFontType());
        assertEquals("14px", loginPage.getUserNameFieldFontSize());

        assertEquals("\"DM Sans\", Arial, Helvetica, sans-serif", loginPage.getPasswordFontType());
        assertEquals("14px", loginPage.getPasswordFontSize());

        assertEquals("\"DM Sans\", Arial, Helvetica, sans-serif", loginPage.getLoginButtonFontType());
        assertEquals("16px", loginPage.getLoginButtonFontSize());
        assertEquals("#3ddc91", loginPage.getLoginButtonColor());

        loginPage.clickLogin();
        assertEquals("14px", loginPage.getErrorMessageFontSize());
        assertEquals("\"DM Sans\", Arial, Helvetica, sans-serif", loginPage.getErrorMessageFontType());
        assertEquals("#e2231a", loginPage.getErrorMessageColor());


    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
