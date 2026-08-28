package saucedemotests;

import org.openqa.selenium.chrome.ChromeOptions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.*;

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
        ChromeOptions options = new ChromeOptions(); if (System.getenv("JENKINS_HOME") != null) { options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage"); } driver = new ChromeDriver(options);
        driver.manage().window().maximize();
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

        assertEquals("Sauce Labs Bike Light", checkout2.getInventoryItemName(0));
        assertEquals("A red light isn't the desired state in testing but it sure helps when riding your bike at night. Water-resistant with 3 lighting modes, 1 AAA battery included.", checkout2.getInventoryItemDescription(0));
        assertEquals("Sauce Labs Bolt T-Shirt", checkout2.getInventoryItemName(1));
        assertEquals("Get your testing superhero on with the Sauce Labs bolt T-shirt. From American Apparel, 100% ringspun combed cotton, heather gray with red bolt.", checkout2.getInventoryItemDescription(1));

    }
    @Test
    public void makeSuccesfullPurchaseValidateAndClickFinishButton(){
        //user makes succesful purchase and is redirected to  the last page
        assertEquals("#3ddc91", checkout2.getFinishButtonColor());
        checkout2.clickFinishButton();
        assertEquals("https://www.saucedemo.com/checkout-complete.html",driver.getCurrentUrl());
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
