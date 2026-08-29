package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckOutPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public CheckOutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }


    private By firstNameField = By.id("first-name");
    private By lastNameField = By.id("last-name");
    private By zipPostalCode = By.id("postal-code");
    private By continueButton = By.id("continue");
    private By cancelButton = By.id("cancel");
    private By errorMessage = By.xpath("//*[@id=\"checkout_info_container\"]/div/form/div[1]/div[4]");
    private By XerrorButton = By.className("error-button");

    public void enterFirstname(String Firstname) {
        ClickUtil.type(driver, driver.findElement(firstNameField), Firstname);
    }

    public void enterLastname(String Lastname) {
        ClickUtil.type(driver, driver.findElement(lastNameField), Lastname);
    }

    public void enterZipPostalCode(String zipCode) {
        ClickUtil.type(driver, driver.findElement(zipPostalCode), zipCode);
    }

    public void enterAllUserDataSucess() {
        ClickUtil.type(driver, driver.findElement(firstNameField), "Muhamed");
        ClickUtil.type(driver, driver.findElement(lastNameField), "Mavmudoski");
        ClickUtil.type(driver, driver.findElement(zipPostalCode), "5020");
    }

    public void clickContinueButton() {
        ClickUtil.click(driver, driver.findElement(continueButton));
    }

    public void clickCancelButton() {
        ClickUtil.click(driver, driver.findElement(cancelButton));
        wait.until(ExpectedConditions.urlContains("cart.html"));
    }

    public String getErrormessageText() {
        return driver.findElement(errorMessage).getText();
    }

    public String getErrorMessageColor() {
        Color color = Color.fromString(driver.findElement(errorMessage).getCssValue("background-color"));
        return color.asHex();
    }

    public String getContinueButtonColor() {
        Color color = Color.fromString(driver.findElement(continueButton).getCssValue("background-color"));
        return color.asHex();
    }

    public boolean isErrorMessageDisplayed() {

        try {
            String errorText = driver.findElement(errorMessage).getText();

            if (errorText.equals("")) {
                return false;
            } else {
                return true;
            }

        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void clickXonerrorButton(){
        ClickUtil.click(driver, driver.findElement(XerrorButton));
    }
}
