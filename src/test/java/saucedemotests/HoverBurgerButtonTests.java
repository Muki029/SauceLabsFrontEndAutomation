package saucedemotests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.Color;
import pages.Header;
import pages.HoverBurgerButtonElements;
import pages.LoginPage;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HoverBurgerButtonTests {
    private WebDriver driver;
    private LoginPage loginPage;
    private Header header;
    private HoverBurgerButtonElements elements;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");

        loginPage = new LoginPage(driver);
        header = new Header(driver);
        elements = new HoverBurgerButtonElements(driver);

        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();
    }

    @Test
    public void hoverTextColorAllButtonsTest() throws InterruptedException {
        header.clickBurgerButton();
        Thread.sleep(2000);

        List<WebElement> menuItems = new ArrayList<>();
        menuItems.add(elements.allItemsElement());
        menuItems.add(elements.aboutButtonElement());
        menuItems.add(elements.logOutElement());
        menuItems.add(elements.resetAppState());

        for (WebElement item : menuItems) {
            elements.hoverOverElement(item);
            Thread.sleep(1000);

            String rgbaColor = item.getCssValue("color");
            String hexColor = Color.fromString(rgbaColor).asHex();



            assertEquals("#3ddc91", hexColor.toLowerCase());

            assertEquals("All Items", header.getAllItemsButtonText());
            assertEquals("About", header.getAboutButtonText());
            assertEquals("Logout", header.getLogoutButtonText());
            assertEquals("Reset App State", header.getResetAppStateText());
        }
    }

    @Test
    public void hoverTextColorAllButtonsTestFORLOOP(){
        header.clickBurgerButton();

        List<WebElement> menuItems = new ArrayList<>();
        menuItems.add(elements.allItemsElement());
        menuItems.add(elements.aboutButtonElement());
        menuItems.add(elements.logOutElement());
        menuItems.add(elements.resetAppState());

        for (int i = 0; i < menuItems.size(); i++) {
            WebElement currentItem = menuItems.get(i);
            elements.hoverOverElement(currentItem);


            Color color = Color.fromString(currentItem.getCssValue("color"));
        }
    }

    @AfterEach
    public void TearDown() {
      driver.quit();
        }
    }