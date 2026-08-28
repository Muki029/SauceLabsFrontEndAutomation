package saucedemotests;

import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.Header;
import pages.LoginPage;
import pages.ProductsPage;

import static org.junit.Assert.assertEquals;

public class HeaderTests {
    private WebDriver driver;
    private LoginPage loginPage;
    private Header header;


    @BeforeEach
    public void setUp() {
        //initalize chrome driver
        ChromeOptions options = new ChromeOptions(); if (System.getenv("JENKINS_HOME") != null) { options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage"); } driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");

        loginPage = new LoginPage(driver);
        header = new Header(driver);

        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();
        loginPage.pressSpacebar();

    }

    @Test
    public void headerNameTest() {
        assertEquals("Swag Labs", header.getHeaderLogoName());
    }


    @Test
    public void burgerButtonAllItems() throws InterruptedException {

        header.clickBurgerButton();
        Thread.sleep(2000);
        header.clickAllItemsButton();
        assertEquals("All Items", header.getAllItemsButtonText());
        assertEquals("https://www.saucedemo.com/inventory.html", driver.getCurrentUrl());
    }

    @Test
    public void burgerAboutButton() throws InterruptedException {
        header.clickBurgerButton();

        Thread.sleep(1000);

        assertEquals("About", header.getAboutButtonText());
        header.clickAboutButton();
        //thread if needed here ->
        assertEquals("https://saucelabs.com/", driver.getCurrentUrl());
    }

    @Test
    public void burgerLogoutButton() throws InterruptedException {
        header.clickBurgerButton();
        Thread.sleep(3000);

        assertEquals("Logout", header.getLogoutButtonText());
        header.clickLogOutButton();

        Thread.sleep(3000);
        assertEquals("https://www.saucedemo.com/", driver.getCurrentUrl());
    }

    @Test
    public void burgerResetAppStateButton() throws InterruptedException {
        header.clickBurgerButton();
        Thread.sleep(2000);
        assertEquals("Reset App State", header.getResetAppStateText());
    }


    @AfterEach
    public void tearOff() {
        driver.quit();
    }
}
