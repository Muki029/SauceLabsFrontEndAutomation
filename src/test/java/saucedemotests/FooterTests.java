package saucedemotests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.Footer;
import pages.Header;
import pages.LoginPage;
import pages.ProductsPage;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class FooterTests {

    private WebDriver driver;
    private LoginPage loginPage;
    private Footer footer;



    @BeforeEach
    public void setUp() {
        //initalize chrome driver
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");

        loginPage = new LoginPage(driver);
        footer = new Footer(driver);

        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();
        loginPage.pressSpacebar();
    }

    @Test
    public void clickFooterButtonTwitterandTextunder() {

        assertEquals("© 2026 Sauce Labs. All Rights Reserved. Terms of Service | Privacy Policy", footer.footerMessage());
        footer.clickTwitterbutton();
        footer.switchToNewTab();
        assertEquals("https://x.com/saucelabs", driver.getCurrentUrl());
    }

    @Test
    public void facebookClick() {

        footer.clickFacebookButton();
        footer.switchToNewTab();
        assertEquals("https://www.facebook.com/saucelabs", driver.getCurrentUrl());

    }

    @Test
    public void linkedInButtonClick() {
        footer.clickLinkedInButton();
        footer.switchToNewTab();
        assertEquals("https://www.linkedin.com/company/sauce-labs/", driver.getCurrentUrl());
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }


}
