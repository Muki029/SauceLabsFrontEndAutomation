package saucedemotests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.CartPage;
import pages.CheckOutPage;
import pages.LoginPage;
import pages.ProductsPage;

import static org.junit.jupiter.api.Assertions.*;

public class CheckOutPageTests {

    private WebDriver driver;
    private LoginPage loginPage;
    private ProductsPage productpage;
    private CartPage cartpage;
    private CheckOutPage checkout;

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

        loginPage.sucessfullLogin("standard_user", "secret_sauce");
        loginPage.pressSpacebar();

        cartpage.clickCartButton();
        Thread.sleep(1400);
        cartpage.clickCheckoutButton();
    }

    @Test
    public void validateTitleName() {
        assertEquals("Checkout: Your Information", productpage.getPageTitle());
    }

    @Test
    //This method "enterAllUserDataSucess" = Automatically populates personal data
    public void enterAllFieldsSucessfully() {
        checkout.enterAllUserDataSucess();
        checkout.clickContinueButton();
    }

    @Test
    //Manually populate personal data.
    public void enterFieldsManually() {
        checkout.enterFirstname("Muhamed");
        checkout.enterLastname("Mavmudoski");
        checkout.enterZipPostalCode("5020");
        checkout.clickContinueButton();
    }

    @Test
    public void validateErrorMessagewithoutenteringData() {
        checkout.clickContinueButton();
        assertTrue(checkout.isErrorMessageDisplayed());
    }

    @Test
    public void loseTheErrorMessage() throws InterruptedException {
        checkout.clickContinueButton();
        assertTrue(checkout.isErrorMessageDisplayed());;
        checkout.clickXonerrorButton();
        assertFalse(checkout.isErrorMessageDisplayed());
    }

    @Test
    public void getColorsFromErrorMessageAndContinueButton() {
        assertEquals("#3ddc91", checkout.getContinueButtonColor());
        checkout.clickContinueButton();
        assertEquals("#e2231a", checkout.getErrorMessageColor());
    }

    @Test
    //in this test we gonna validate the text from the error message which is displayed when no name or username and zipcode is sent
    public void getTextFromErrorMessage(){
        checkout.clickContinueButton();
        assertEquals("Error: First Name is required",checkout.getErrormessageText());
        checkout.clickXonerrorButton();
        checkout.enterFirstname("Muhamed");
        checkout.clickContinueButton();
        assertEquals("Error: Last Name is required",checkout.getErrormessageText());
        checkout.clickXonerrorButton();
        checkout.enterFirstname("Muhamed");
        checkout.enterLastname("Mavmudoski");
        checkout.clickContinueButton();
        assertEquals("Error: Postal Code is required",checkout.getErrormessageText());
        checkout.clickXonerrorButton();
    }

    @Test
    public void validateWhatDoesCancelButton(){
        checkout.clickCancelButton();
        //user is redirected to the previous page which is CartPage
        assertEquals("https://www.saucedemo.com/cart.html",driver.getCurrentUrl());
    }

    @Test
    public void sucessfullyProceedToNextPage(){
        checkout.enterAllUserDataSucess();
        checkout.clickContinueButton();
        assertEquals("https://www.saucedemo.com/checkout-step-two.html",driver.getCurrentUrl());
    }


    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
