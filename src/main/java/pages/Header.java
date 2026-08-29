package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Header {
    private WebDriver driver;
    private WebDriverWait wait;

    public Header(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private By headerLogo = By.className("app_logo");
    private By burgerButton = By.id("react-burger-menu-btn");
    private By allItemsButton = By.id("inventory_sidebar_link");
    private By aboutButton = By.id("about_sidebar_link");
    private By logOutButton = By.id("logout_sidebar_link");
    private By resetAppState = By.id("reset_sidebar_link");

    public String getHeaderLogoName() {
        return driver.findElement(headerLogo).getText();
    }

    public void clickBurgerButton() {
        ClickUtil.click(driver, driver.findElement(burgerButton));
        wait.until(ExpectedConditions.visibilityOfElementLocated(aboutButton));
    }

    public String getAllItemsButtonText() {
        return driver.findElement(allItemsButton).getText();
    }

    public void clickAllItemsButton() {
        ClickUtil.click(driver, driver.findElement(allItemsButton));
    }

    public void clickAboutButton() {
        ClickUtil.click(driver, driver.findElement(aboutButton));
    }

    public String getAboutButtonText() {
        return driver.findElement(aboutButton).getText();
    }

    public void clickLogOutButton() {
        ClickUtil.click(driver, driver.findElement(logOutButton));

    }

    public String getLogoutButtonText() {
        return driver.findElement(logOutButton).getText();
    }

    public void clickResetAppState() {
        ClickUtil.click(driver, driver.findElement(resetAppState));
    }

    public String getResetAppStateText() {
        return driver.findElement(resetAppState).getText();
    }

}
