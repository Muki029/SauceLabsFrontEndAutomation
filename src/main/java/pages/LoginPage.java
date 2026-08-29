package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;

import java.awt.*;
import java.awt.event.KeyEvent;

public class LoginPage {

    private WebDriver driver;


    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    //locators
    private By usernameField = By.id("user-name");
    private By passwordField = By.id("password");
    private By loginButton = By.id("login-button");
    private By errorMessage = By.cssSelector(".error-message-container.error");
    private By errorMessageButtonX = By.className("error-button");

    //Actions
    public void pressSpacebar() {
        try {
            Thread.sleep(1000); // чека popup-от да се појави

            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_SPACE);
            robot.keyRelease(KeyEvent.VK_SPACE);

        } catch (Exception ignored) {
        }
    }

    public void enterUsername(String value) {
        driver.findElement(usernameField).sendKeys(value);
    }

    public void enterPassword(String value) {
        driver.findElement(passwordField).sendKeys(value);
    }

    public void clickLogin() {
        ClickUtil.click(driver, driver.findElement(loginButton));
    }

    public String GetErrorMessage() {
        return driver.findElement(errorMessage).getText();
    }

    public void clickErrorMessageXButton() {
        ClickUtil.click(driver, driver.findElement(errorMessageButtonX));
    }

    public Boolean isErrorMessageDisplayed() {
        try {
            driver.findElement(errorMessage).getText();
            return true;

        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public String getUserNameFieldFontType() {
        return driver.findElement(usernameField).getCssValue("font-family");
    }

    public String getUserNameFieldFontSize() {
        return driver.findElement(usernameField).getCssValue("font-size");
    }

    public String getPasswordFontType() {
        return driver.findElement(passwordField).getCssValue("font-family");
    }

    public String getPasswordFontSize() {
        return driver.findElement(passwordField).getCssValue("font-size");
    }

    public String getLoginButtonText() {
        return driver.findElement(loginButton).getAttribute("value");
    }

    public String getLoginButtonFontType() {
        return driver.findElement(loginButton).getCssValue("font-family");
    }

    public String getLoginButtonFontSize() {
        return driver.findElement(loginButton).getCssValue("font-size");
    }

    public String getLoginButtonColor() {
        Color loginButtonColorBackground = Color.fromString(driver.findElement(loginButton).getCssValue("background-color"));
        return loginButtonColorBackground.asHex();
    }

    public String getErrorMessageFontSize() {
        return driver.findElement(errorMessage).getCssValue("font-size");
    }

    public String getErrorMessageFontType() {
        return driver.findElement(errorMessage).getCssValue("font-family");
    }

    public String getErrorMessageColor() {
        Color errorMessageColor = Color.fromString(driver.findElement(errorMessage).getCssValue("background-color"));
        return errorMessageColor.asHex();
    }

    public void sucessfullLogin(String username,String password){
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        clickLogin();
    }
}
