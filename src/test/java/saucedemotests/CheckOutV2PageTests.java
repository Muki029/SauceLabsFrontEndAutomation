package saucedemotests;

import org.openqa.selenium.chrome.ChromeOptions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckOutV2PageTests {
    private WebDriver driver;
    private LoginPage loginPage;
    private ProductsPage productpage;
    private CartPage cartpage;
    private CheckOutPage checkout;
    private CheckOutV2Page checkout2;

    @BeforeEach
    public void setUp() throws InterruptedException {
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
        productpage = new ProductsPage(driver);
        cartpage = new CartPage(driver);
        checkout = new CheckOutPage(driver);
        checkout2 = new CheckOutV2Page(driver);

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
    }

    @Test
    public void checkPricewithTaxIncludedTest() {
        assertEquals(32.39, checkout2.calculateTotalPriceWithTax());
    }

    @Test
    public void checkItempriceWithoutTax() {
        assertEquals(29.99, checkout2.getItemTotalPricewithoutTax());
    }

    @Test
    public void checkTaxPriceOnly() {
        assertEquals(2.40, checkout2.getOnlyTaxPriceFromItems());
    }

    @Test
    public void validateEveryTextFieldfromCheckoutV2PageFields() {
        assertEquals("Payment Information:", checkout2.getPaymentInfomationText());
        assertEquals("SauceCard #31337", checkout2.getPaymentInformationdescriptionText());
        assertEquals("Shipping Information:", checkout2.getShippingInformationText());
        assertEquals("Free Pony Express Delivery!", checkout2.getShippingInformationDescriptionText());
        assertEquals("Price Total", checkout2.getPricetext());
    }


    @Test
    public void checkItemNameAndDescriptionFromFactorySelectedItem() {
        assertEquals("Sauce Labs Backpack", checkout2.getInventoryItemName(0));
        assertEquals("carry.allTheThings() with the sleek, streamlined Sly Pack that melds uncompromising style with unequaled laptop and tablet protection.", checkout2.getInventoryItemDescription(0));
    }

    @Test
    public void clickCancelButtonAndRedirect() {
        checkout2.clickCancelButton();
        //user should be redirected to products page again
        assertEquals("https://www.saucedemo.com/inventory.html", driver.getCurrentUrl());
    }

    @Test
    public void makeSuccesfullPurchaseValidateAndClickFinishButton(){
        //user makes succesful purchase and is redirected to  the last page
        assertEquals("#3ddc91", checkout2.getFinishButtonColor());
        checkout2.clickFinishButton();
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.urlContains("checkout-complete.html"));
        assertEquals("https://www.saucedemo.com/checkout-complete.html",driver.getCurrentUrl());
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
