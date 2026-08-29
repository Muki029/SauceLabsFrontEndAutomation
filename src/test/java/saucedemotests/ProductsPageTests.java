package saucedemotests;

import org.openqa.selenium.chrome.ChromeOptions;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.LoginPage;
import pages.ProductsPage;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ProductsPageTests {
    private WebDriver driver;
    private LoginPage loginPage;
    private ProductsPage productpage;


    @BeforeEach
    public void setUp() throws InterruptedException {
        //initalize chrome driver
        ChromeOptions options = new ChromeOptions(); if (System.getenv("BUILD_NUMBER") != null || System.getenv("JENKINS_URL") != null) { options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage"); } driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");

        loginPage = new LoginPage(driver);
        productpage = new ProductsPage(driver);

        loginPage.sucessfullLogin("standard_user", "secret_sauce");
        loginPage.pressSpacebar();
    }

    @Test
    public void orderingDropdownValuesTest() {
        assertEquals("Name (A to Z)", productpage.getAllOptionsFromSortingDropdown().get(0).getText());
        assertEquals("Name (Z to A)", productpage.getAllOptionsFromSortingDropdown().get(1).getText());
        assertEquals("Price (low to high)", productpage.getAllOptionsFromSortingDropdown().get(2).getText());
        assertEquals("Price (high to low)", productpage.getAllOptionsFromSortingDropdown().get(3).getText());
    }

    @Test
    public void orderingProductsFromHighToLowPriceTest() {
        productpage.selectOrderingDropdownOption(3);

        assertEquals("Price (high to low)", productpage.getTextFromOrderingDropdown());
        assertTrue(productpage.areAllProductsPricesDescending());

    }

    @Test
    public void orderingProductsFromLowToHighriceTest() {
        productpage.selectOrderingDropdownOption(2);

        assertEquals("Price (low to high)", productpage.getTextFromOrderingDropdown());
        assertTrue(productpage.areAllProductsPricesIncreasing());
    }

    @Test
    public void orderingProductsZtoAAlphabeticallyTest() {
        List<String> initialNamesList = productpage.getAllProductsNames();

        productpage.selectOrderingDropdownOption(1);

        List<String> SortedinitialNamesList = productpage.getAllProductsNames();

        Collections.reverse(initialNamesList);

        assertEquals(initialNamesList, SortedinitialNamesList);
    }

    @Test
    public void orderingProductsAtoZAlphabeticallyTest() {
        List<String> initialNamesList = productpage.getAllProductsNames();

        productpage.selectOrderingDropdownOption(0);

        List<String> SortedinitialNamesList = productpage.getAllProductsNames();

        Collections.sort(initialNamesList);

        assertEquals(initialNamesList, SortedinitialNamesList);
    }

    @Test
    public void validateColorChangeOnProductTitleHoverTest() {

        assertEquals("#18583a", productpage.getColorFromBackPackTitle());

        productpage.hoverBackpackTitle();

        assertEquals("#3ddc91", productpage.getColorFromBackPackTitle());
    }

    @Test
    public void validateBurgerMenuItemsColorsTest() throws InterruptedException {
        productpage.clickBurgerButton();

        Thread.sleep(1000);

        List<String> actualColors = productpage.getBurgerMenuColorsOnHover();

        assertEquals("#3ddc91", actualColors.get(0));
        assertEquals("#3ddc91", actualColors.get(1));
        assertEquals("#3ddc91", actualColors.get(2));
        assertEquals("#3ddc91", actualColors.get(3));

    }

    @Test
    public void validateBurgerMenuItemsTextTest() throws InterruptedException {
        productpage.clickBurgerButton();
        Thread.sleep(100);

        List<String> actualText = productpage.getBurgerMenuTextOnHover();

        assertEquals("All Items", actualText.get(0));
        assertEquals("About", actualText.get(1));
        assertEquals("Logout", actualText.get(2));
        assertEquals("Reset App State", actualText.get(3));
    }

    @Test
    public void getAllProductsItemsHoveringColor() throws InterruptedException {
        List<String> actualHoveredColor = productpage.getAllProductsHoverColor();

        assertEquals("#3ddc91", actualHoveredColor.get(0));
        assertEquals("#3ddc91", actualHoveredColor.get(1));
        assertEquals("#3ddc91", actualHoveredColor.get(2));
        assertEquals("#3ddc91", actualHoveredColor.get(3));
        assertEquals("#3ddc91", actualHoveredColor.get(4));
        assertEquals("#3ddc91", actualHoveredColor.get(5));
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
