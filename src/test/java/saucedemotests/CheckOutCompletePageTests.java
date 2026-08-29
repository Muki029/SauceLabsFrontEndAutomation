package saucedemotests;

import org.openqa.selenium.chrome.ChromeOptions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckOutCompletePageTests {
    private WebDriver driver;
    private LoginPage loginPage;
    private ProductsPage productpage;
    private CartPage cartpage;
    private CheckOutPage checkout;
    private CheckOutV2Page checkout2;
    private CheckOutCompletePage complete;

    @BeforeEach
    public void setUp() throws InterruptedException {
        //initalize chrome driver
        ChromeOptions options = new ChromeOptions(); if (System.getenv("BUILD_NUMBER") != null || System.getenv("JENKINS_URL") != null) { options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage", "--window-size=1920,1080"); } driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");

        loginPage = new LoginPage(driver);
        productpage = new ProductsPage(driver);
        cartpage = new CartPage(driver);
        checkout = new CheckOutPage(driver);
        checkout2 = new CheckOutV2Page(driver);
        complete = new CheckOutCompletePage(driver);

        loginPage.sucessfullLogin("standard_user", "secret_sauce");
        loginPage.pressSpacebar();

        cartpage.addItemToCartButton(0);
        Thread.sleep(1500);
        cartpage.clickCartButton();
        Thread.sleep(2500);
        cartpage.clickCheckoutButton();
        checkout.enterAllUserDataSucess();
        checkout.clickContinueButton();
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.urlContains("checkout-step-two.html"));
        checkout2.clickFinishButton();
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.urlContains("checkout-complete.html"));
    }
    @Test
    public void validateAllTextFieldsFromThePage(){
        assertEquals("Checkout: Complete!",productpage.getPageTitle());
        assertEquals("Thank you for your order!",complete.getCompleteHeaderText());
        assertEquals("Your order has been dispatched, and will arrive just as fast as the pony can get there!",complete.getCompleteHeaderDescriptionText());
        assertEquals("#3ddc91",complete.getBackHomeButtonColor());
        assertTrue(complete.isPonyExpressImageSpecsCorrect());
    }

    @Test
    public void clickBackHomeAndValidateUserIsRedirected(){
        complete.clickBackHomeButton();
        assertEquals("https://www.saucedemo.com/inventory.html",driver.getCurrentUrl());
    }
    @AfterEach
    public void tearDown(){
        driver.quit();
    }
}
