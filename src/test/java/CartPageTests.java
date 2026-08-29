import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.CartPage;
import pages.LoginPage;
import pages.ProductsPage;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CartPageTests {

    private WebDriver driver;
    private LoginPage loginPage;
    private ProductsPage productpage;
    private CartPage cartpage;

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

        loginPage.sucessfullLogin("standard_user", "secret_sauce");
        loginPage.pressSpacebar();

        //get into cart page
        Thread.sleep(1000);
        cartpage.clickCartButton();
    }


    @Test
    public void getCartPageTitle() {
        assertEquals("Your Cart", productpage.getPageTitle());
    }


    @Test
    public void chooseItemAndValidateItemNames() throws InterruptedException {
        //because we have @Beforeeach annotation which redirect us direct on cart page,
        // we need to go back to products page first to select items

        cartpage.clickContinueShoppingButton();
        Thread.sleep(1000);
        cartpage.addItemToCartButton(0);
        Thread.sleep(1000);
        cartpage.clickCartButton();
        Thread.sleep(1000);
        assertEquals("Sauce Labs Backpack", cartpage.getItemInCartName());
        Thread.sleep(1000);
        cartpage.clickRemoveButton();
        Thread.sleep(1000);
        cartpage.clickContinueShoppingButton();
        Thread.sleep(1000);
        cartpage.addItemToCartButton(1);
        Thread.sleep(1000);
        cartpage.clickCartButton();
        assertEquals("Sauce Labs Bike Light", cartpage.getItemInCartName());
    }

    @Test
    public void validateCheckoutAndRemoveButtonColors() throws InterruptedException {
        cartpage.clickContinueShoppingButton();
        Thread.sleep(1000);
        cartpage.addItemToCartButton(0);
        Thread.sleep(1000);
        cartpage.clickCartButton();
        Thread.sleep(1000);
        assertEquals("#3ddc91", cartpage.getColorOfCheckoutButton());
        assertEquals("#e2231a", cartpage.getColorOfRemoveButton());
    }


    @Test
    public void isItemSucessfullyAddedToCartTestthrowException() {
        cartpage.clickContinueShoppingButton();
        //we dont click add to cart so we expect exception here
        assertFalse(cartpage.isItemAddedToCart());
    }

    @Test
    public void isItemSucessfullyAddedToCartTestadd() {
        cartpage.clickContinueShoppingButton();
        cartpage.addItemToCartButton(0);
        assertTrue(cartpage.isItemAddedToCart());
    }

    @Test
    public void addSingleItemByClickingOnNameAndValidateRemoveButton() {
        cartpage.clickContinueShoppingButton();
        cartpage.clickOnInventoryItemName(0);
        cartpage.clickAddToCartSingleItemButton();

        assertTrue(cartpage.isItemAddedToCart());
        assertEquals("#e2231a", cartpage.getColorFromRemoveButton());
        cartpage.clickRemoveSingleItemButton();
        assertFalse(cartpage.isItemAddedToCart());
    }

    @Test
    public void getPricefromSelectedItemandNameAlsoTest() throws InterruptedException {
        cartpage.clickContinueShoppingButton();
        Thread.sleep(1000);
        cartpage.addItemToCartButton(1);
        Thread.sleep(1000);
        cartpage.clickCartButton();
        assertEquals("9.99", cartpage.getPriceOfItemInCart());
        assertEquals("Sauce Labs Bike Light", cartpage.getItemInCartName());
        assertEquals(1, cartpage.getItemsCountInCart());
    }

    @Test
    public void addMoreItemsAndCheckQuantityFieldTest() throws InterruptedException {
        cartpage.clickContinueShoppingButton();
        cartpage.addItemToCartButton(0);
        cartpage.addItemToCartButton(1);
        cartpage.addItemToCartButton(2);
        Thread.sleep(500);
        cartpage.clickCartButton();
        Thread.sleep(1000);
        assertEquals(3, cartpage.getItemsCountInCart());

    }

    @Test
    public void sucessfullyProceedToCheckoutPage() {
        cartpage.clickCheckoutButton();
        assertEquals("https://www.saucedemo.com/checkout-step-one.html", driver.getCurrentUrl());
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
