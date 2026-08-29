package saucedemotests;

import org.openqa.selenium.chrome.ChromeOptions;

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
        boolean isCi = System.getenv("BUILD_NUMBER") != null || System.getenv("JENKINS_URL") != null;
        ChromeOptions options = new ChromeOptions();
        if (isCi) {
            options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage", "--window-size=1920,1080");
        }
        driver = new ChromeDriver(options);
        if (!isCi) {
            driver.manage().window().maximize();
        }
        driver.get("https://www.saucedemo.com/");

        loginPage = new LoginPage(driver);
        footer = new Footer(driver);

        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();
        loginPage.pressSpacebar();
    }

    @Test
    public void footerCopyrightTextTest() {
        assertEquals("© 2026 Sauce Labs. All Rights Reserved. Terms of Service | Privacy Policy", footer.footerMessage());
    }

    @Test
    public void twitterButtonClick() {
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
