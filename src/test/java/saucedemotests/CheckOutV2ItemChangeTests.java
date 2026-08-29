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

//split out of CheckOutV2PageTests: these tests change what's in the cart mid-test
//(remove/add items) and recalculate the totals, instead of using the default backpack
public class CheckOutV2ItemChangeTests {
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
    public void removeItemChangeforAnother() throws InterruptedException {
        checkout2.clickCancelButton();
        Thread.sleep(1000);

        cartpage.removeItemFromCartButton(0);
        cartpage.addItemToCartButton(1);
        cartpage.clickCartButton();

        Thread.sleep(1000);
        cartpage.clickCheckoutButton();
        checkout.enterAllUserDataSucess();
        checkout.clickContinueButton();
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.urlContains("checkout-step-two.html"));

        assertEquals("https://www.saucedemo.com/checkout-step-two.html", driver.getCurrentUrl());
        assertEquals(9.99, checkout2.getItemTotalPricewithoutTax());
        assertEquals(0.80, checkout2.getOnlyTaxPriceFromItems());
        assertEquals(10.79, checkout2.calculateTotalPriceWithTax(), 0.1);
    }

    @Test
    public void addMoreItemsandCalculateThemTogether() throws InterruptedException {
        checkout2.clickCancelButton();


        cartpage.removeItemFromCartButton(0);
        cartpage.addItemToCartButton(1);
        cartpage.addItemToCartButton(2);
        cartpage.addItemToCartButton(3);
        cartpage.clickCartButton();

        cartpage.clickCheckoutButton();
        checkout.enterAllUserDataSucess();
        checkout.clickContinueButton();
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.urlContains("checkout-step-two.html"));

        assertEquals(75.97, checkout2.getItemTotalPricewithoutTax());
        assertEquals(6.08, checkout2.getOnlyTaxPriceFromItems());
        assertEquals(82.05, checkout2.calculateTotalPriceWithTax());

    }

    @Test
    public void getInventoryItemsNamesAndDescriptionTest() {
        checkout2.clickCancelButton();
        cartpage.removeItemFromCartButton(0);
        cartpage.addItemToCartButton(1);
        cartpage.addItemToCartButton(2);
        cartpage.clickCartButton();
        cartpage.clickCheckoutButton();
        checkout.enterAllUserDataSucess();
        checkout.clickContinueButton();
        new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.urlContains("checkout-step-two.html"));

        assertEquals("Sauce Labs Bike Light", checkout2.getInventoryItemName(0));
        assertEquals("A red light isn't the desired state in testing but it sure helps when riding your bike at night. Water-resistant with 3 lighting modes, 1 AAA battery included.", checkout2.getInventoryItemDescription(0));
        assertEquals("Sauce Labs Bolt T-Shirt", checkout2.getInventoryItemName(1));
        assertEquals("Get your testing superhero on with the Sauce Labs bolt T-shirt. From American Apparel, 100% ringspun combed cotton, heather gray with red bolt.", checkout2.getInventoryItemDescription(1));

    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
