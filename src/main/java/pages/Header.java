package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Header {
    private WebDriver driver;

    public Header(WebDriver driver) {
        this.driver = driver;
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
        driver.findElement(burgerButton).click();
    }

    public String getAllItemsButtonText() {
        return driver.findElement(allItemsButton).getText();
    }

    public void clickAllItemsButton() {
        driver.findElement(allItemsButton).click();
    }

    public void clickAboutButton() {
        driver.findElement(aboutButton).click();
    }

    public String getAboutButtonText() {
        return driver.findElement(aboutButton).getText();
    }

    public void clickLogOutButton() {
        driver.findElement(logOutButton).click();

    }

    public String getLogoutButtonText() {
        return driver.findElement(logOutButton).getText();
    }

    public void clickResetAppState() {
        driver.findElement(resetAppState).click();
    }

    public String getResetAppStateText() {
        return driver.findElement(resetAppState).getText();
    }

}
